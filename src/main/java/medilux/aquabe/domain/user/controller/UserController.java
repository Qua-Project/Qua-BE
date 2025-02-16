package medilux.aquabe.domain.user.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import medilux.aquabe.domain.user.dto.*;
import medilux.aquabe.domain.user.service.S3ImageService;
import medilux.aquabe.domain.user.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.nio.charset.StandardCharsets;



@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final S3ImageService s3ImageService;

    //카카오 로그인 & 회원가입
    @GetMapping("/login/kakao")
    public ResponseEntity<Void> kakaoLogin(@RequestParam("code") String accessCode, HttpServletResponse httpServletResponse) {
        userService.oAuthKakaoLogin(accessCode, httpServletResponse);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/login/apple")
    public ResponseEntity<Void> appleLogin(@RequestParam("code") String authorizationCode, HttpServletResponse httpServletResponse) {
        userService.oAuthAppleLogin(authorizationCode, httpServletResponse);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> findUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginEmail = authentication.getName();
        return ResponseEntity.ok(userService.findUser(loginEmail));
    }

    @PostMapping(value = "/sign-up", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserSignUpResponse> signUp(
            @RequestPart(value = "userDetails") String userDetailsJson,
            @RequestPart(value = "userImage", required = false) MultipartFile userImage
    ) {
        try {
            // ObjectMapper에 JavaTimeModule 등록
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());

            // JSON 문자열 -> DTO 변환
            UserSignUpRequest request = objectMapper.readValue(
                    new String(userDetailsJson.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8),
                    UserSignUpRequest.class
            );

            // 이미지 업로드 처리
            String imageUrl = "";
            if (userImage != null && !userImage.isEmpty()) {
                imageUrl = s3ImageService.upload(userImage);
            }

            // 서비스 호출
            UserSignUpResponse response = userService.signUp(request, imageUrl);
            return ResponseEntity.status(response.getStatus()).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    UserSignUpResponse.builder()
                            .status(500)
                            .message("서버 내부 오류: " + e.getMessage())
                            .build()
            );
        }
    }
    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody UserLoginRequest request, HttpServletResponse httpServletResponse) {
        userService.login(request, httpServletResponse);
        return ResponseEntity.ok().build();
    }

    // 사용자 정보 조회
//    @GetMapping("/{user_id}")
//    public ResponseEntity<UserSignUpResponse> getUser(@PathVariable("user_id") UUID userId) {
//        UserSignUpResponse response = userService.getUserById(userId);
//        return ResponseEntity.ok(response);
//    }

    // 사용자 정보 수정
    @PutMapping("/me")
    public ResponseEntity<UserUpdateResponse> updateUser(
            @RequestBody UserUpdateRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginEmail = authentication.getName();
        UserUpdateResponse response = userService.updateUser(loginEmail, request);
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/me/updateImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateUserImage(
            @RequestParam(name = "profileImage", required = false) MultipartFile profileImage
    ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginEmail = authentication.getName();
        userService.updateUserImage(loginEmail, profileImage);
        return ResponseEntity.ok("profile image updated");
    }

    // 사용자 삭제
    @DeleteMapping("/me")
    public ResponseEntity<UserDeleteResponse> deleteUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginEmail = authentication.getName();
        UserDeleteResponse response = userService.deleteUser(loginEmail);
        return ResponseEntity.ok(response);
    }
}
