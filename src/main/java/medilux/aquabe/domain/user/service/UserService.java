
package medilux.aquabe.domain.user.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import medilux.aquabe.auth.JwtTokenUtil;
import medilux.aquabe.auth.KakaoUtil;
import medilux.aquabe.domain.user.dto.*;
import medilux.aquabe.domain.user.entity.UserEntity;
import medilux.aquabe.domain.user.repository.UserRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final S3ImageService s3ImageService;

    private final KakaoUtil kakaoUtil;


    @Value("${spring.jwt.secret}")
    private String secretKey;

    @Value("${spring.jwt.expired-time}")
    private Long expiredMs;

    //카카오 회원가입 & 로그인
    @Transactional
    public UserEntity oAuthLogin(String accessCode, HttpServletResponse httpServletResponse) {
        KakaoResponse.OAuthToken oAuthToken = kakaoUtil.requestToken(accessCode);
        System.out.println("oAuthToken 출력 = " + oAuthToken);
        KakaoResponse.KakaoProfile kakaoProfile = kakaoUtil.requestProfile(oAuthToken);
        String email = kakaoProfile.getKakaoAccount().getEmail();

        UserEntity user = userRepository.findByEmail(email)
                .orElseGet(() -> createNewUser(kakaoProfile));

        String token = JwtTokenUtil.createToken(user.getEmail(), secretKey, expiredMs);
        httpServletResponse.setHeader("Authorization", token);

        return user;
    }

    private UserEntity createNewUser(KakaoResponse.KakaoProfile kakaoProfile) {
        UserEntity newUser = UserEntity.builder()
                .username(kakaoProfile.getKakaoAccount().getProfile().getNickname())
                .email(kakaoProfile.getKakaoAccount().getEmail())
                .password("1234")
                .build();
        return userRepository.save(newUser);
    }

    // 회원가입
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
                .password(request.getPassword())
                .email(request.getEmail())
                .telephone(request.getTelephone())
                .userAge(request.getUserAge())
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
                .password(userEntity.getPassword()) // 실제 비밀번호 대신 암호화된 데이터 권장
                .telephone(userEntity.getTelephone())
                .userAge(userEntity.getUserAge())
                .userImage(imageUrl)
                .gender(userEntity.getGender())
                .birthDate(userEntity.getBirthDate())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    // 사용자 로그인
    public UserLoginResponse login(UserLoginRequest request) {
        UserEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("이메일 또는 비밀번호가 잘못되었습니다."));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 잘못되었습니다.");
        }

        return UserLoginResponse.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .telephone(user.getTelephone())
                .userImage(user.getUserImage())
                .build();
    }


    // 사용자 정보 조회
    public UserSignUpResponse getUserById(UUID userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        return UserSignUpResponse.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .telephone(user.getTelephone())
                .userAge(user.getUserAge())
                .userImage(user.getUserImage())
                .gender(user.getGender())
                .birthDate(user.getBirthDate())
                .build();
    }

    // 사용자 정보 수정
    public UserUpdateResponse updateUser(UUID userId, UserUpdateRequest request) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        user.update(request.getUsername(), request.getEmail(), request.getTelephone(),
                request.getUserImage(), request.getUserAge());
        userRepository.save(user);

        return UserUpdateResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .telephone(user.getTelephone())
                .userImage(user.getUserImage())
                .userAge(user.getUserAge())
                .build();
    }

    // 사용자 삭제
    public UserDeleteResponse deleteUser(UUID userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        userRepository.delete(user);
        // s3에서 이미지 삭제
        s3ImageService.deleteImageFromS3(user.getUserImage());

        return UserDeleteResponse.builder()
                .message("사용자 삭제에 성공했습니다.")
                .build();
    }

    @Transactional(readOnly = true)
    public UserEntity getLoginUserByEmail(String loginId) {
        if(loginId == null){
            new BadRequestException("로그인 후 사용가능합니다.");
        }
        UserEntity user = userRepository.findByEmail(loginId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        return user;
    }
}