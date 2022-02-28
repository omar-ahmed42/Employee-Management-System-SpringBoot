package com.omar_ahmed42.employeemanagementsystem.service;

import com.omar_ahmed42.employeemanagementsystem.exception.BadRequestException;
import com.omar_ahmed42.employeemanagementsystem.exception.EmployeeNotFoundException;
import com.omar_ahmed42.employeemanagementsystem.exception.NotFoundException;
import com.omar_ahmed42.employeemanagementsystem.model.Employee;
import com.omar_ahmed42.employeemanagementsystem.model.Employee_;
import com.omar_ahmed42.employeemanagementsystem.repo.EmployeeRepo;
import com.omar_ahmed42.employeemanagementsystem.specification.EmployeeSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Objects;

@Service @Transactional
public class EmployeeService {

    private final EmployeeRepo employeeRepo;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public EmployeeService(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    public void addEmployee(Employee employee){

        if (employeeRepo.existsByEmail(employee.getEmail())){
            throw new BadRequestException("Email already exists");
        }
        employeeRepo.save(employee);
    }

    public Employee getEmployee(long id){
        return employeeRepo.findById(id).orElseThrow(() -> new EmployeeNotFoundException("Employee with id "+  id + " not found"));
    }

    public Page<Employee> searchForEmployee(long id, String firstName, String lastName, String email, String phoneNumber,
                                            String jobTitle, String sort, int page, int size){

        ArrayList<Specification<Employee>> specifications = new ArrayList<>();
        specifications.add(EmployeeSpecification.employeeHasID(id));
        specifications.add(EmployeeSpecification.employeeFirstNameContains(firstName));
        specifications.add(EmployeeSpecification.employeeEmailContains(email));
        specifications.add(EmployeeSpecification.employeeLastNameContains(lastName));
        specifications.add(EmployeeSpecification.employeeHasPhoneNumber(phoneNumber));
        specifications.add(EmployeeSpecification.employeeHasJobTitle(jobTitle));

        Sort.Direction sort_ =  Objects.isNull(sort) || sort.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(sort_, "id"));
        Specification<Employee> resultSpecification = specifications.get(0);
        for (int i = 1; i < specifications.size(); i++){
            resultSpecification = Specification.where(resultSpecification).or(specifications.get(i));
        }

        return employeeRepo.findAll(resultSpecification, pageable);
    }

    public Page<Employee> getPageOfEmployees(int page, int size, Sort sort){
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<Employee> pageOfEmployees = employeeRepo.findAll(pageable);
        if (page > pageOfEmployees.getTotalPages()){
            throw new NotFoundException("Page " + page + " does not exist");
        }

        return pageOfEmployees;
    }

    public void deleteEmployee(long id){
        if (!employeeRepo.existsById(id)){
            throw new EmployeeNotFoundException("Employee with ID " + id + " not found");
        }
        employeeRepo.deleteEmployeeById(id);
    }

    public void deleteEmployee(String email){
        if (!employeeRepo.existsByEmail(email)){
            throw new EmployeeNotFoundException("Employee with Email " + email + " not found");
        }
        employeeRepo.deleteEmployeeByEmail(email);
    }

    public void updateEmployee(long id, String firstName, String lastName, String email, String phoneNumber,
                               String jobTitle,
                               String imageURL){
        if (!employeeRepo.existsById(id)){
            throw new EmployeeNotFoundException("Employee with id " + id + " not found");
        }

        CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
        CriteriaUpdate<Employee> employeeCriteriaUpdate = criteriaBuilder.createCriteriaUpdate(Employee.class);
        Root<Employee> root = employeeCriteriaUpdate.from(Employee.class);

        if (Objects.nonNull(firstName)){
            employeeCriteriaUpdate.set(Employee_.firstName, firstName);
        }

        if (Objects.nonNull(lastName)){
                employeeCriteriaUpdate.set(Employee_.lastName, lastName).set(Employee_.email, email);
        }

        if (Objects.nonNull(email)){
            employeeCriteriaUpdate.set(Employee_.email, email);
        }

        if (Objects.nonNull(phoneNumber)){
            employeeCriteriaUpdate.set(Employee_.phoneNumber, phoneNumber);
        }

        if (Objects.nonNull(jobTitle)){
            employeeCriteriaUpdate.set(Employee_.jobTitle, jobTitle);
        }

        if (Objects.nonNull(imageURL)){
            employeeCriteriaUpdate.set(Employee_.imageURL, imageURL);
        }
        employeeCriteriaUpdate.where(criteriaBuilder.equal(root.get(Employee_.id), id));

        this.entityManager.createQuery(employeeCriteriaUpdate).executeUpdate();
    }


}
