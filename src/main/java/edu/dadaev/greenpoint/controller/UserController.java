package edu.dadaev.greenpoint.controller;

import edu.dadaev.greenpoint.dto.UserRequestDTO;
import edu.dadaev.greenpoint.dto.UserResponseDTO;
import edu.dadaev.greenpoint.security.CustomUserDetails;
import edu.dadaev.greenpoint.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserController {
    private UserService userService;


    @PostMapping("/register")
    public void register(@RequestBody UserRequestDTO userRequestDTO){
        userService.createUser(userRequestDTO);

    }

    @GetMapping("/users/me")
    public UserResponseDTO me(@AuthenticationPrincipal CustomUserDetails userDetails){
        return userService.getInfo(userDetails.getId());
    }
}
