package com.omar_ahmed42.employeemanagementsystem.controller;

import com.omar_ahmed42.employeemanagementsystem.exception.BadRequestException;
import com.omar_ahmed42.employeemanagementsystem.exception.NotFoundException;
import com.omar_ahmed42.employeemanagementsystem.model.Role;
import com.omar_ahmed42.employeemanagementsystem.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1")
@RestController
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/role")
    public ResponseEntity<?> addRole(@RequestBody Role role){
        try{
            roleService.addRole(role);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (BadRequestException badRequestException){
            return ResponseEntity.badRequest().body(badRequestException.getMessage());
        }
    }

    @PutMapping("/role")
    public ResponseEntity<?> updateRole(@RequestParam(name = "id") long id, @RequestParam(name = "name") String roleName){
        try{
            roleService.updateRole(id, roleName);
            return ResponseEntity.noContent().build();
        }catch (NotFoundException notFoundException){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/role")
    public ResponseEntity<?> deleteRole(@RequestParam(name = "id") long id){
        try{
            roleService.deleteRole(id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException notFoundException){
            return ResponseEntity.notFound().build();
        }
    }


}