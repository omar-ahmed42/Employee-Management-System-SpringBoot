package com.omar_ahmed42.employeemanagementsystem.specification;

import com.omar_ahmed42.employeemanagementsystem.model.Employee;
import com.omar_ahmed42.employeemanagementsystem.model.Employee_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.text.MessageFormat;

public class EmployeeSpecification {
    public static Specification<Employee> employeeHasJobTitle(String jobTitle){
        return new Specification<>() {
            @Override
            public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get(Employee_.jobTitle), jobTitle);
            }
        };
    }

    public static Specification<Employee> employeeFirstNameContains(String firstName){
        return new Specification<>() {
            @Override
            public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(root.get(Employee_.firstName), contains(firstName));
            }
        };
    }
    public static Specification<Employee> employeeLastNameContains(String lastName){
        return new Specification<>() {
            @Override
            public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(root.get(Employee_.lastName), contains(lastName));
            }
        };
    }

    public static Specification<Employee> employeeHasPhoneNumber(String phoneNumber){
        return new Specification<>() {
            @Override
            public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get(Employee_.phoneNumber), phoneNumber);
            }
        };
    }

    public static Specification<Employee> employeeEmailContains(String email){
        return new Specification<>() {
            @Override
            public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(root.get(Employee_.email), contains(email));
            }
        };
    }

    public static Specification<Employee> employeeHasID(long id){
        return new Specification<>() {
            @Override
            public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get((Employee_.id)), id);
            }
        };
    }

    private static String contains(String expression) {
        return MessageFormat.format("%{0}%", expression);
    }

}
