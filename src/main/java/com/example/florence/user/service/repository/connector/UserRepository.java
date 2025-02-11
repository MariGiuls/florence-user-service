package com.example.florence.user.service.repository.connector;

import com.example.florence.user.service.repository.model.DBUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<DBUser, Integer> {
}
