package com.example.florence_user_service.repository.connector;

import com.example.florence_user_service.repository.model.DBUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<DBUser, String> {
}
