package com.company.restapi.service;

import com.company.restapi.model.User;
import com.company.restapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        User user =  userRepository.findById(id)
                .orElseThrow(() ->{
                    throw new RuntimeException("USER NOT FOUND");
                });
        return user;
    }

    public User createUser(User newUser) {
        return userRepository.save(newUser);
    }

    public User updateUser(User newUser, Long id) {
        User oldUser = userRepository.findById(id)
                .orElseThrow(() ->{
                    throw new RuntimeException("USER NOT FOUND");
                });
        oldUser.setName(newUser.getName());
        oldUser.setEmail(newUser.getEmail());
        return userRepository.save(oldUser);
    }

    public void deleteUser(Long userId) {
        User user =  userRepository.findById(userId)
                .orElseThrow(() ->{
                    throw new RuntimeException("USER NOT FOUND");
                });
        userRepository.delete(user);
    }

    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).
                orElseThrow(
                        () -> {
                            throw new RuntimeException("User not found");}
                );
        return user;
    }


}
