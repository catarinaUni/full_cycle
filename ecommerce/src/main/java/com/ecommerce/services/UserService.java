package com.ecommerce.services;

import com.ecommerce.dtos.UserDTO;
import com.ecommerce.dtos.UserRequestDTO;
import com.ecommerce.entities.User;
import com.ecommerce.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public UserDTO register(UserRequestDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email já cadastrado.");
        }

        User user = new User(
                dto.getName(),
                dto.getEmail(),
                dto.getPassword(),
                dto.getAddress(),
                null
        );

        User savedUser = userRepository.save(user);
        return new UserDTO(savedUser);
    }

    public UserDTO login(String email, String password) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty() || !optionalUser.get().getPassword().equals(password)) {
            throw new RuntimeException("Email ou senha inválidos.");
        }

        return new UserDTO(optionalUser.get());
    }

    public List<UserDTO> findAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }

    public UserDTO findById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
        return new UserDTO(user);
    }
}
