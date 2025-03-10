
package medilux.aquabe.domain.user.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import medilux.aquabe.auth.AppleUtil;
import medilux.aquabe.auth.JwtTokenUtil;
import medilux.aquabe.auth.KakaoUtil;
import medilux.aquabe.domain.user.dto.*;
import medilux.aquabe.domain.user.entity.UserEntity;
import medilux.aquabe.domain.user.repository.UserRepository;
import medilux.aquabe.domain.vanity.entity.UserVanityEntity;
import medilux.aquabe.domain.vanity.repository.UserVanityRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;

import medilux.aquabe.common.error.exceptions.BadRequestException;
import static medilux.aquabe.common.error.ErrorCode.*;


import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserVanityRepository userVanityRepository;

    private final S3ImageService s3ImageService;

    private final KakaoUtil kakaoUtil;

    private final AppleUtil appleUtil;


    @Value("${spring.jwt.secret}")
    private String secretKey;

    @Value("${spring.jwt.expired-time}")
    private Long expiredMs;

    //카카오 회원가입 & 로그인
    @Transactional
    public void oAuthKakaoLogin(String accessCode, HttpServletResponse httpServletResponse){
        KakaoResponse.OAuthToken oAuthToken = kakaoUtil.requestToken(accessCode);
        KakaoResponse.KakaoProfile kakaoProfile = kakaoUtil.requestProfile(oAuthToken);
        String email = kakaoProfile.getKakaoAccount().getEmail();

        UserEntity user = userRepository.findByEmail(email)
                .orElseGet(() -> createNewKakaoUser(kakaoProfile));

        String token = JwtTokenUtil.createToken(user.getEmail(), secretKey, expiredMs);

        httpServletResponse.setHeader("Authorization", "Bearer " + token);

    }


    private UserEntity createNewKakaoUser(KakaoResponse.KakaoProfile kakaoProfile) {
        UserEntity newUser = UserEntity.builder()
                .email(kakaoProfile.getKakaoAccount().getEmail())
                .build();
        //디폴트 프로필 이미지 넣기

        return userRepository.save(newUser);
    }

    @Transactional
    public void oAuthAppleLogin(String identityToken, HttpServletResponse httpServletResponse) {
        AppleResponse.AppleUser appleUser = appleUtil.getUserInfoFromToken(identityToken);
        String sub = appleUser.getSub();

        UserEntity user = userRepository.findByAppleSub(sub)
                .orElseGet(() -> createNewAppleUser(appleUser));

        String token = JwtTokenUtil.createToken(user.getEmail(), secretKey, expiredMs);
        httpServletResponse.setHeader("Authorization", "Bearer " + token);
    }


    private UserEntity createNewAppleUser(AppleResponse.AppleUser appleUser) {

        //디폴트 프로필 이미지 넣기
        UserEntity newUser = UserEntity.builder()
                .email(appleUser.getEmail())
                .appleSub(appleUser.getSub())
                .build();

        return userRepository.save(newUser);
    }





    @Transactional(readOnly = true)
    public UserResponse findUser(String loginEmail){
        UserEntity user = userRepository.findByEmail(loginEmail)
                .orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "존재하지 않는 사용자입니다."));


        LocalDate birthDate = user.getBirthDate();
        Integer userAge = (birthDate != null) ? calculateAge(birthDate) : 0;

        return UserResponse.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .userImage(user.getUserImage())
                .birthDate(birthDate)
                .userAge(userAge)
                .gender(user.getGender())
                .username(user.getUsername()).build();

    }

    private Integer calculateAge(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now(ZoneId.systemDefault())).getYears();
    }

    // 회원가입
    @Transactional
    public UserSignUpResponse signUp(UserSignUpRequest request, String imageUrl) {
        // 닉네임 중복 확인
        if (userRepository.existsByUsername(request.getUsername())) {
            return UserSignUpResponse.builder()
                    .status(409)
                    .message("사용 중인 닉네임입니다.")
                    .build();
        }

//        String imageUrl = "";
//        if(!imageFile.isEmpty()) {
//            imageUrl = s3ImageService.upload(imageFile);
//        }

        // 사용자 생성 및 저장
        UserEntity userEntity = UserEntity.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .userImage(imageUrl)
                .gender(request.getGender())
                .birthDate(request.getBirthDate())
                .build();
        userRepository.save(userEntity);

        // 응답 생성
        return UserSignUpResponse.builder()
                .status(201)
                .message("회원가입에 성공했습니다.")
                .userId(userEntity.getUserId())
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .userImage(imageUrl)
                .gender(userEntity.getGender())
                .birthDate(userEntity.getBirthDate())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    // 사용자 로그인
    @Transactional(readOnly = true)
    public void login(UserLoginRequest loginRequest, HttpServletResponse httpServletResponse) {
        UserEntity user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "존재하지 않는 사용자입니다."));

        String token = JwtTokenUtil.createToken(user.getEmail(), secretKey, expiredMs);

        httpServletResponse.setHeader("Authorization", "Bearer " + token);

    }

    // 사용자 정보 수정
    @Transactional
    public UserUpdateResponse updateUser(String loginEmail, UserUpdateRequest request) {
        UserEntity user = userRepository.findByEmail(loginEmail)
                .orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "존재하지 않는 사용자입니다."));

        user.update(request.getUsername(), request.getBirthDate(), request.getGender());
        userRepository.saveAndFlush(user);

        userVanityRepository.findById(user.getUserId()).orElseGet(() -> {
            UserVanityEntity vanity = UserVanityEntity.builder()
                    .user(user)
                    .build();

            userVanityRepository.save(vanity);
            return vanity;
        });

        return UserUpdateResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .birthDate(user.getBirthDate())
                .gender(user.getGender())
                .build();
    }

    @Transactional
    public void updateUserImage(String loginEmail, MultipartFile file) {
        UserEntity user = userRepository.findByEmail(loginEmail)
                .orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "존재하지 않는 사용자입니다."));
        String imageUrl = s3ImageService.upload(file);
        user.updateUserImage(imageUrl);
        userRepository.save(user);
    }

    @Transactional
    public UserDeleteResponse deleteUser(String loginEmail) {
        UserEntity user = userRepository.findByEmail(loginEmail)
                .orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "존재하지 않는 사용자입니다."));


        userRepository.delete(user);

        s3ImageService.deleteImageFromS3(user.getUserImage());

        return UserDeleteResponse.builder()
                .message("사용자 삭제에 성공했습니다.")
                .build();
    }


    @Transactional(readOnly = true)
    public UserEntity getLoginUserByEmail(String loginEmail) {
        if(loginEmail == null){
            new BadRequestException(INVALID_PARAMETER, "로그인이 필요한 서비스입니다.");
        }
        UserEntity user = userRepository.findByEmail(loginEmail)
                .orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "존재하지 않는 사용자입니다."));
        return user;
    }
}