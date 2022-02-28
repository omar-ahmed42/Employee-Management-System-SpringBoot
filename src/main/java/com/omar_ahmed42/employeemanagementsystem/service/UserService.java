package com.omar_ahmed42.employeemanagementsystem.service;

import com.omar_ahmed42.employeemanagementsystem.exception.BadRequestException;
import com.omar_ahmed42.employeemanagementsystem.exception.NotFoundException;
import com.omar_ahmed42.employeemanagementsystem.model.User;
import com.omar_ahmed42.employeemanagementsystem.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service @Transactional
public class UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public void addUser(User user){
        if (userRepo.existsByUsername(user.getUsername())){
            throw new BadRequestException("Username already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
    }

    public User getUser(long id){
        return userRepo.findById(id).orElseThrow(() -> new NotFoundException("User with id " + id + " not found"));
    }

    public User getUser(String username){
        return userRepo.findByUsername(username).orElseThrow(() -> new NotFoundException("User with username " + username + " not found"));
    }

    public void updateUsername(long id, String username){
        if (!userRepo.existsById(id)){
            throw new NotFoundException("User with id " + id + " not found");
        }
        userRepo.updateUsername(id, username);
    }
    public void updatePassword(long id, String password){
        if (!userRepo.existsById(id)){
            throw new NotFoundException("User with id " + id + " not found");
        }
        userRepo.updatePassword(id, password);
    }

    public void delete(long id){
        if (!userRepo.existsById(id)){
            throw new NotFoundException("User with id " + id + " not found");
        }
        userRepo.deleteById(id);
    }
}
