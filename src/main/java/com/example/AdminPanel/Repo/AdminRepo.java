package com.example.AdminPanel.Repo;

import com.example.AdminPanel.Entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepo extends JpaRepository<Admin,Integer> {
    Admin findAdminByEmail(String email);
}
