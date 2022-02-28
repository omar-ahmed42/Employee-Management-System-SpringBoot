package com.omar_ahmed42.employeemanagementsystem.service;

import com.omar_ahmed42.employeemanagementsystem.exception.EmployeeNotFoundException;
import com.omar_ahmed42.employeemanagementsystem.exception.UserNotFoundException;
import com.omar_ahmed42.employeemanagementsystem.model.Employee;
import com.omar_ahmed42.employeemanagementsystem.model.User;
import com.omar_ahmed42.employeemanagementsystem.repo.EmployeeRepo;
import com.omar_ahmed42.employeemanagementsystem.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service @Transactional
public class UserEmployeeService {

    private final UserRepo userRepo;
    private final EmployeeRepo employeeRepo;

    @Autowired
    public UserEmployeeService(UserRepo userRepo, EmployeeRepo employeeRepo) {
        this.userRepo = userRepo;
        this.employeeRepo = employeeRepo;
    }

    public void mapEmployeeToUser(long userID, long employeeID){
        User user = userRepo.findById(userID).orElseThrow(
                () -> new UserNotFoundException("User with id " + userID + " not found"));

        Employee employee = employeeRepo.findById(employeeID).orElseThrow(
                () -> new EmployeeNotFoundException("Employee with id " + employeeID + " not found"));

        user.setEmployee(employee);
        userRepo.save(user);
    }
}
