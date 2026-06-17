package edu.dadaev.greenpoint.service;


import edu.dadaev.greenpoint.dto.UpdateUserDTO;
import edu.dadaev.greenpoint.dto.UserMapper;
import edu.dadaev.greenpoint.dto.UserRequestDTO;
import edu.dadaev.greenpoint.dto.UserResponseDTO;
import edu.dadaev.greenpoint.entity.User;
import edu.dadaev.greenpoint.enumerated.Role;
import edu.dadaev.greenpoint.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void createUser(UserRequestDTO userRequestDTO){
        User entity = new User();
        entity.setEmail(userRequestDTO.email());
        entity.setPassword(passwordEncoder.encode(userRequestDTO.password()));
        entity.setFirstName(userRequestDTO.firstName());
        entity.setLastName(userRequestDTO.lastName());
        entity.setRole(Role.USER);
        userRepository.save(entity);
        System.out.println("user registered");
    }

    @Transactional
    public UserResponseDTO updateUser(UpdateUserDTO updateUserDTO, Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("пользователь не найден"));
        user.setFirstName(updateUserDTO.firstName());
        user.setLastName(updateUserDTO.lastName());
        userRepository.save(user);
        return userMapper.toDto(user);

    }

    public UserResponseDTO getInfo(Long id){
        return userRepository.findById(id).map(userMapper::toDto).orElseThrow(RuntimeException::new);
    }
}
