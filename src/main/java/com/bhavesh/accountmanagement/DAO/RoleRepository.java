package com.bhavesh.accountmanagement.DAO;

import com.bhavesh.accountmanagement.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
