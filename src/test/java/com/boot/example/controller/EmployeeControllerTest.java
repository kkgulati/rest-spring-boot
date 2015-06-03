package com.boot.example.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.boot.example.domain.Employee;
import com.boot.example.service.EmployeeSvc;
import com.boot.example.util.EmployeeUtil;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeControllerTest {

    @Mock
    private EmployeeSvc employeeSvc;

    private EmployeeController employeeController;

    @Before
    public void setUp() throws Exception {
        employeeController = new EmployeeController(employeeSvc);
    }

    @Test
    public void shouldCreateEmployee() throws Exception {
        final Employee savedEmployee = stubServiceToReturnStoredEmployee();
        final Employee employee = EmployeeUtil.createEmployee();
        Employee returnedEmployee = employeeController.createEmployee(employee);
        // verify user was passed to EmployeeSvc
        verify(employeeSvc, times(1)).save(employee);
        assertEquals("Returned user should come from the service", savedEmployee, returnedEmployee);
    }

    private Employee stubServiceToReturnStoredEmployee() {
        final Employee employee = EmployeeUtil.createEmployee();
        when(employeeSvc.save(any(Employee.class))).thenReturn(employee);
        return employee;
    }


    @Test
    public void shouldListAllEmployees() throws Exception {
        stubServiceToReturnExistingEmployees(10);
        Collection<Employee> employees = employeeController.listEmployees();
        assertNotNull(employees);
        assertEquals(10, employees.size());
        // verify user was passed to EmployeeSvc
        verify(employeeSvc, times(1)).getList();
    }

    private void stubServiceToReturnExistingEmployees(int howMany) {
        when(employeeSvc.getList()).thenReturn(EmployeeUtil.createEmployeeList(howMany));
    }

}
