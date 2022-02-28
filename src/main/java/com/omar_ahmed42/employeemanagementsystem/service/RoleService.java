package com.omar_ahmed42.employeemanagementsystem.service;

import com.omar_ahmed42.employeemanagementsystem.exception.BadRequestException;
import com.omar_ahmed42.employeemanagementsystem.exception.RoleNotFoundException;
import com.omar_ahmed42.employeemanagementsystem.model.Role;
import com.omar_ahmed42.employeemanagementsystem.repo.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service @Transactional
public class RoleService {

    private final RoleRepo roleRepo;

    @Autowired
    public RoleService(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    public void addRole(Role role){
        if (roleRepo.existsByName(role.getName())){
            throw new BadRequestException("Role with name " + role.getName() + " already exists");
        }
        roleRepo.save(role);
    }

    public void updateRole(long id, String name){
        if (!roleRepo.existsById(id)){
            throw new RoleNotFoundException("Role with name " + name + " not found");
        }
        roleRepo.updateName(id, name);
    }

    public void deleteRole(long id){
        if (!roleRepo.existsById(id)){
            throw new RoleNotFoundException("Role with ID " + id + " not found");
        }
        roleRepo.deleteRoleById(id);
    }

}
