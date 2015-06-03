package com.boot.example.service;

import java.util.List;

import com.boot.example.domain.Employee;

public interface EmployeeSvc {

    Employee save(Employee employee);

    List<Employee> getList();

}
