package edu.dadaev.greenpoint.controller;

import edu.dadaev.greenpoint.dto.UpdateUserDTO;
import edu.dadaev.greenpoint.dto.UserRequestDTO;
import edu.dadaev.greenpoint.dto.UserResponseDTO;
import edu.dadaev.greenpoint.security.CustomUserDetails;
import edu.dadaev.greenpoint.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class UserController {
    private UserService userService;


    @PostMapping("/register")
    public void register(@RequestBody UserRequestDTO userRequestDTO){
        userService.createUser(userRequestDTO);
    }

    @GetMapping("/users/me")
    public ResponseEntity<UserResponseDTO> getMe(@AuthenticationPrincipal CustomUserDetails userDetails){
        return ResponseEntity.ok(userService.getInfo(userDetails.getId()));
    }


    @PutMapping("/users/me")
    public ResponseEntity<UserResponseDTO> updateUser(@RequestBody UpdateUserDTO updateUserDTO, @AuthenticationPrincipal CustomUserDetails userDetails){
        return ResponseEntity.ok(userService.updateUser(updateUserDTO, userDetails.getId()));
    }
}
