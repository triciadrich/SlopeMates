package com.example.slopemates.Repositories;

import com.example.slopemates.Models.ConnectionRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConnectionRequestRepository extends JpaRepository<ConnectionRequest, Long> {
    List<ConnectionRequest> findAllById(Long userId);
}
