package com.omar_ahmed42.employeemanagementsystem.repo;

import com.omar_ahmed42.employeemanagementsystem.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface EmployeeRepo extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {
    @Override
    Employee save(Employee employee);

    @Override
    Optional<Employee> findById(Long id);

    boolean existsById(long id);
    boolean existsByEmail(String email);

    @Override
    void delete(Employee employee);

    void deleteEmployeeById(long id);
    void deleteEmployeeByEmail(String email);

    @Override
    Page<Employee> findAll(Pageable pageable);

    @Override
    Page<Employee> findAll(Specification spec, Pageable pageable);
}
