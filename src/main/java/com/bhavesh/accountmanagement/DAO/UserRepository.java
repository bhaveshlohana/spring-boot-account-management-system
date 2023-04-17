package com.bhavesh.accountmanagement.DAO;

import com.bhavesh.accountmanagement.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
