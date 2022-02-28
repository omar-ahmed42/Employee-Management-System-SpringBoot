package com.omar_ahmed42.employeemanagementsystem.repo;

import com.omar_ahmed42.employeemanagementsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {

    @Override
    Optional<User> findById(Long id);
    Optional<User> findByUsername(String username);

    @Override
    User save(User user);

    @Override
    boolean existsById(Long id);
    boolean existsByUsername(String username);


    @Modifying
    @Query("UPDATE User SET username = :username WHERE id = :id")
    void updateUsername(@Param("id") long id, @Param("username") String username);

    @Modifying
    @Query("UPDATE User SET password = :password WHERE id = :id")
    void updatePassword(@Param("id") long id, @Param("password") String password);


    @Override
    void deleteById(Long id);

    @Override
    void delete(User entity);
}
