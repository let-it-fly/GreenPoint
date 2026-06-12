package edu.dadaev.greenpoint.service;


import edu.dadaev.greenpoint.dto.UserMapper;
import edu.dadaev.greenpoint.dto.UserRequestDTO;
import edu.dadaev.greenpoint.dto.UserResponseDTO;
import edu.dadaev.greenpoint.entity.User;
import edu.dadaev.greenpoint.repository.UserRepository;
import lombok.AllArgsConstructor;
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
        User entity = userMapper.toEntity(userRequestDTO);
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        entity.setFirstName(userRequestDTO.firstName());
        entity.setLastName(userRequestDTO.lastName());
        userRepository.save(entity);
        System.out.println("user registered");

    }

    public UserResponseDTO getInfo(Long id){
        return userRepository.findById(id).map(userMapper::toDto).orElseThrow(RuntimeException::new);
    }
}
