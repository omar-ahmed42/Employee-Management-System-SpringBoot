package com.omar_ahmed42.employeemanagementsystem.controller;

import com.omar_ahmed42.employeemanagementsystem.exception.BadRequestException;
import com.omar_ahmed42.employeemanagementsystem.exception.NotFoundException;
import com.omar_ahmed42.employeemanagementsystem.model.User;
import com.omar_ahmed42.employeemanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public ResponseEntity<?> addUser(@RequestBody User user){
        try{
            userService.addUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (BadRequestException badRequestException){
            return ResponseEntity.badRequest().body(badRequestException.getMessage());
        }
    }

    @GetMapping("/user")
    public ResponseEntity<User> getUser(@RequestParam(name = "id") long id){
        try{
            User user = userService.getUser(id);
            return ResponseEntity.ok(user);
        }catch (NotFoundException notFoundException){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/user/update/username")
    public ResponseEntity<?> updateUsername(long id, String username){
        try{
            userService.updateUsername(id, username);
            return ResponseEntity.noContent().build();
        }catch (NotFoundException notFoundException){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/user/update/password")
    public ResponseEntity<?> updatePassword(long id, String password){
        try{
            userService.updatePassword(id, password);
            return ResponseEntity.noContent().build();
        }catch (NotFoundException notFoundException){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/user")
    public ResponseEntity<?> deleteUser(@RequestParam(name = "id") long id){
        try{
            userService.delete(id);
            return ResponseEntity.noContent().build();
        }catch (NotFoundException notFoundException){
            return ResponseEntity.notFound().build();
        }
    }
}
