package com.example.demoblogapp.service;

import com.example.demoblogapp.dto.UserDTO;
import com.example.demoblogapp.entity.User;
import com.example.demoblogapp.exception.NotFoundException;
import com.example.demoblogapp.repository.UserRepository;
import com.example.demoblogapp.request.UpsertUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> dtoUserList = new ArrayList<>();
        users.forEach(user -> {
            UserDTO userDTO = convertToDTO(user);
            dtoUserList.add(userDTO);
        });
        return dtoUserList;
    }

    @Transactional
    public UserDTO addUser(UpsertUserRequest request) {
        User user = User.builder()
                .name(request.getName())
                .avatar(request.getAvatar())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();
        userRepository.save(user);
        return convertToDTO(user);
    }

    public UserDTO getUserById(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Not found user with id = " + id);
        });
        return convertToDTO(user);
    }

    @Transactional
    public UserDTO updateUser(Integer id, UpsertUserRequest request) {
        User user = userRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Not found user with id = " + id);
        });
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setAvatar(request.getAvatar());
        user.setPassword(request.getPassword());
        userRepository.save(user);

        return convertToDTO(user);
    }

    @Transactional
    public void deleteUser(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Not found user with id = " + id);
        });
        userRepository.delete(user);
    }

    private UserDTO convertToDTO(User model) {
        UserDTO dto = new UserDTO();
        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setEmail(model.getEmail());
        dto.setAvatar(model.getAvatar());
        return dto;
    }
}
