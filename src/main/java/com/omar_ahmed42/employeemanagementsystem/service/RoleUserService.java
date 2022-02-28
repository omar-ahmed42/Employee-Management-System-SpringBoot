package com.omar_ahmed42.employeemanagementsystem.service;

import com.omar_ahmed42.employeemanagementsystem.exception.EmployeeNotFoundException;
import com.omar_ahmed42.employeemanagementsystem.exception.RoleNotFoundException;
import com.omar_ahmed42.employeemanagementsystem.model.Role;
import com.omar_ahmed42.employeemanagementsystem.model.User;
import com.omar_ahmed42.employeemanagementsystem.repo.RoleRepo;
import com.omar_ahmed42.employeemanagementsystem.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service @Transactional
public class RoleUserService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;

    @Autowired
    public RoleUserService(UserRepo userRepo, RoleRepo roleRepo) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }

    public void addRoleToUser(long userID, long roleID){
        User user = userRepo.findById(userID).orElseThrow(
                () -> new EmployeeNotFoundException("Employee with id " + userID + " not found"));

        Role role = roleRepo.findById(roleID).orElseThrow(
                () -> new RoleNotFoundException("Role with id " + roleID + " not found"));

        user.getRoles().add(role);
        userRepo.save(user);
    }
}
