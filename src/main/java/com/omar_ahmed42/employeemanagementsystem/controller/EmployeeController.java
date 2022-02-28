package com.omar_ahmed42.employeemanagementsystem.controller;

import com.omar_ahmed42.employeemanagementsystem.exception.BadRequestException;
import com.omar_ahmed42.employeemanagementsystem.exception.EmployeeNotFoundException;
import com.omar_ahmed42.employeemanagementsystem.exception.NotFoundException;
import com.omar_ahmed42.employeemanagementsystem.model.Employee;
import com.omar_ahmed42.employeemanagementsystem.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RequestMapping("/api/v1")
@RestController
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/employee")
    public ResponseEntity<?> addEmployee(@RequestBody Employee employee){
        try{
            employeeService.addEmployee(employee);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (BadRequestException badRequestException){
            return ResponseEntity.badRequest().body(badRequestException.getMessage());
        }
    }

    @GetMapping("/employee")
    public ResponseEntity<Employee> getEmployee(@RequestParam(name = "id") long id){
        try {
            Employee employee = employeeService.getEmployee(id);
            return ResponseEntity.ok(employee);
        }catch (EmployeeNotFoundException employeeNotFoundException){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/employees", params = {"page", "size", "sort"})
    public ResponseEntity<Page<Employee>> getPageOfEmployees(@RequestParam(name = "page") int page, @RequestParam(name = "size") int size, @RequestParam(name = "sort", required = false) String sort){
        Sort.Direction sort_ = Objects.isNull(sort) || sort.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        try {
            Page<Employee> pageOfEmployees = employeeService.getPageOfEmployees(page, size, Sort.by(sort_, "id"));
            return ResponseEntity.ok(pageOfEmployees);
        }catch (NotFoundException notFoundException){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/employees/search")
    public ResponseEntity<Page<Employee>> searchForEmployees(@RequestParam(name = "id", required = false) long id,
                                                             @RequestParam(name = "firstName", required = false) String firstName,
                                                             @RequestParam(name = "lastName", required = false) String lastName,
                                                             @RequestParam(name = "email", required = false) String email,
                                                             @RequestParam(name = "phoneNumber", required = false) String phoneNumber,
                                                             @RequestParam(name = "jobTitle", required = false) String jobTitle,
                                                             @RequestParam(name = "sort", required = false) String sort,
                                                             @RequestParam(name = "page") int page, @RequestParam(name = "size") int size){
        Page<Employee> pageOfEmployees = employeeService.searchForEmployee(id, firstName, lastName, email, phoneNumber, jobTitle, sort, page, size);
        return ResponseEntity.ok(pageOfEmployees);

    }

    @DeleteMapping("/employee")
    public ResponseEntity<?> deleteEmployee(@RequestParam(name = "id") long id){
        try {
            employeeService.deleteEmployee(id);
            return ResponseEntity.noContent().build();
        }catch (BadRequestException badRequestException){
            return ResponseEntity.badRequest().body(badRequestException.getMessage());
        }
    }

    @DeleteMapping("/employee/email")
    public ResponseEntity<?> deleteEmployee(@RequestParam(name = "email") String email){
        try {
            employeeService.deleteEmployee(email);
            return ResponseEntity.noContent().build();
        }catch (BadRequestException badRequestException){
            return ResponseEntity.badRequest().body(badRequestException.getMessage());
        }
    }

    @PutMapping("/employee")
    public ResponseEntity<?> updateEmployee(@RequestParam(name = "id") long id,
                                         @RequestParam(name = "firstName", required = false) String firstName,
                                         @RequestParam(name = "lastName", required = false) String lastName,
                                         @RequestParam(name = "email", required = false) String email,
                                         @RequestParam(name = "phoneNumber", required = false) String phoneNumber,
                                         @RequestParam(name = "jobTitle", required = false) String jobTitle,
                                         @RequestParam(name = "imageURL", required = false) String imageURL){
        try {
            employeeService.updateEmployee(id, firstName, lastName, email, phoneNumber, jobTitle, imageURL);
            return ResponseEntity.noContent().build();
        }catch (NotFoundException notFoundException){
            return ResponseEntity.notFound().build();
        }
    }

}