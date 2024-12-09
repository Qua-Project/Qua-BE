package medilux.aquabe.domain.user.controller;

import lombok.RequiredArgsConstructor;
import medilux.aquabe.domain.user.dto.UserSignUpRequest;
import medilux.aquabe.domain.user.dto.UserSignUpResponse;
import medilux.aquabe.domain.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
