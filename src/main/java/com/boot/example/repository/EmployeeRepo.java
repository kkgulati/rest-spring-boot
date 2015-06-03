package com.boot.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boot.example.domain.Employee;

public interface EmployeeRepo extends JpaRepository<Employee, String> {
}
