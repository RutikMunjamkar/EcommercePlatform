package in.org.project.EcommercePlatform.controller;

import in.org.project.EcommercePlatform.dto.LoginRequestDto;
import in.org.project.EcommercePlatform.dto.LoginResponseDto;
import in.org.project.EcommercePlatform.service.AuthService;
import in.org.project.EcommercePlatform.dto.SignUpRequestDto;
import in.org.project.EcommercePlatform.dto.SignUpResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/signUp")
    public ResponseEntity<SignUpResponseDto> signUp(@RequestBody SignUpRequestDto signUpRequestDto){
        return ResponseEntity.ok(authService.signUpUser(signUpRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto){
        return ResponseEntity.ok(authService.loginUser(loginRequestDto));
    }

    @PostMapping("/updatePassword")
    public ResponseEntity<LoginResponseDto> updatePassword(@RequestBody Map<String,Object>requestMap){
        return ResponseEntity.ok(authService.updatePassWord(requestMap));
    }

    @GetMapping("/allusers/{role}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> getAllRoles(@PathVariable String role){
        return ResponseEntity.ok(authService.getAllUsers(role));
    }

    //delete user only access given to admin

}