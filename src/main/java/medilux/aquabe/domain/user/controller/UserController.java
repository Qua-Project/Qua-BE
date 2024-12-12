package medilux.aquabe.domain.user.controller;

import lombok.RequiredArgsConstructor;
import medilux.aquabe.domain.user.dto.*;
import medilux.aquabe.domain.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<UserSignUpResponse> signUp(@RequestBody UserSignUpRequest request) {
        UserSignUpResponse response = userService.signUp(request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginRequest request) {
        UserLoginResponse response = userService.login(request);
        return ResponseEntity.ok(response);
    }

    // 사용자 정보 조회
    @GetMapping("/{user_id}")
    public ResponseEntity<UserSignUpResponse> getUser(@PathVariable("user_id") UUID userId) {
        UserSignUpResponse response = userService.getUserById(userId);
        return ResponseEntity.ok(response);
    }

    // 사용자 정보 수정
    @PutMapping("/{user_id}")
    public ResponseEntity<UserUpdateResponse> updateUser(
            @PathVariable("user_id") UUID userId,
            @RequestBody UserUpdateRequest request) {
        UserUpdateResponse response = userService.updateUser(userId, request);
        return ResponseEntity.ok(response);
    }

    // 사용자 삭제
    @DeleteMapping("/{user_id}")
    public ResponseEntity<UserDeleteResponse> deleteUser(@PathVariable("user_id") UUID userId) {
        UserDeleteResponse response = userService.deleteUser(userId);
        return ResponseEntity.ok(response);
    }
}
