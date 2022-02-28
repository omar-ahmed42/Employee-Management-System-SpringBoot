package com.omar_ahmed42.employeemanagementsystem.controller;

import com.omar_ahmed42.employeemanagementsystem.exception.NotFoundException;
import com.omar_ahmed42.employeemanagementsystem.service.UserEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1")
@RestController
public class UserEmployeeController {

    private final UserEmployeeService userEmployeeService;

    @Autowired
    public UserEmployeeController(UserEmployeeService userEmployeeService) {
        this.userEmployeeService = userEmployeeService;
    }

    @PutMapping("/user")
    public ResponseEntity<?> mapEmployeeToUser(@RequestParam(name = "userId") long userId, @RequestParam(name = "employeeId") long employeeId){
        try {
            userEmployeeService.mapEmployeeToUser(userId, employeeId);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException notFoundException){
            return ResponseEntity.notFound().build();
        }
    }
}