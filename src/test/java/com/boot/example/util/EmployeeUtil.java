package com.boot.example.util;

import java.util.ArrayList;
import java.util.List;

import com.boot.example.domain.Employee;

public class EmployeeUtil {

    private static final String ID = "id";
    private static final String PASSWORD = "password";

    private EmployeeUtil() {
    }

    public static Employee createEmployee() {
        return new Employee(ID, PASSWORD);
    }

    public static List<Employee> createEmployeeList(int num) {
        List<Employee> empList = new ArrayList<>();
        for (int i = 0; i < num; i++) {
        	empList.add(new Employee(ID + "#" + i, PASSWORD));
        }
        return empList;
    }

}
