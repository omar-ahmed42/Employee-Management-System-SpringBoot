package com.omar_ahmed42.employeemanagementsystem.repo;

import com.omar_ahmed42.employeemanagementsystem.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleRepo extends JpaRepository<Role, Long> {
    @Override
    Role save(Role role);

    @Override
    void delete(Role role);

    void deleteRoleById(long id);

    @Modifying
    @Query("UPDATE Role SET name = :name WHERE id = :id")
    void updateName(@Param("id") long id, @Param("name") String name);

    @Override
    boolean existsById(Long id);

    boolean existsByName(String name);
}
