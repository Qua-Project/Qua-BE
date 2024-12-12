
package medilux.aquabe.domain.user.service;

import lombok.RequiredArgsConstructor;
import medilux.aquabe.domain.user.dto.*;
import medilux.aquabe.domain.user.entity.UserEntity;
import medilux.aquabe.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 회원가입
    public UserSignUpResponse signUp(UserSignUpRequest request) {
        // 이메일 중복 확인
        if (userRepository.existsByEmail(request.getEmail())) {
            return UserSignUpResponse.builder()
                    .status(409)
                    .message("사용 중인 이메일입니다.")
                    .build();
        }

        // 사용자 생성 및 저장
        UserEntity userEntity = UserEntity.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .email(request.getEmail())
                .telephone(request.getTelephone())
                .userAge(request.getUserAge())
                .userImage(request.getUserImage())
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
                .userImage(userEntity.getUserImage())
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

        return UserDeleteResponse.builder()
                .message("사용자 삭제에 성공했습니다.")
                .build();
    }
}