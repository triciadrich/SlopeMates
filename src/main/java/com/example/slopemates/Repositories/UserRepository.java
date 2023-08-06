package com.example.slopemates.Repositories;

import com.example.slopemates.Models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findAll();
    Optional<User> findById(Long Id);
    User findByEmail(String email);

}
