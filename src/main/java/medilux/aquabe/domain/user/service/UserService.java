package medilux.aquabe.domain.user.service;

import lombok.RequiredArgsConstructor;
import medilux.aquabe.domain.user.dto.UserSignUpRequest;
import medilux.aquabe.domain.user.dto.UserSignUpResponse;
import medilux.aquabe.domain.user.entity.UserEntity;
import medilux.aquabe.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

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
}
