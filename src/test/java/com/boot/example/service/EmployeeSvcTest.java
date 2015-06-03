package com.boot.example.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
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
import com.boot.example.repository.EmployeeRepo;
import com.boot.example.service.exception.EmployeeExistsException;
import com.boot.example.util.EmployeeUtil;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeSvcTest {

    @Mock
    private EmployeeRepo employeeRepo;

    private EmployeeSvc employeeSvc;

    @Before
    public void setUp() throws Exception {
        employeeSvc = new EmployeeSvcImpl(employeeRepo);
    }

    @Test
    public void shouldSaveNewEmployee_GivenThereDoesNotExistOneWithTheSameId_ThenTheSavedEmployeeShouldBeReturned() throws Exception {
        final Employee savedEmployee = stubRepositoryToReturnEmployeeOnSave();
        final Employee employee = EmployeeUtil.createEmployee();
        final Employee returnedEmployee = employeeSvc.save(employee);
        // verify repository was called with user
        verify(employeeRepo, times(1)).save(employee);
        assertEquals("Returned user should come from the repository", savedEmployee, returnedEmployee);
    }

    private Employee stubRepositoryToReturnEmployeeOnSave() {
        Employee employee = EmployeeUtil.createEmployee();
        when(employeeRepo.save(any(Employee.class))).thenReturn(employee);
        return employee;
    }

    @Test
    public void shouldSaveNewEmployee_GivenThereExistsOneWithTheSameId_ThenTheExceptionShouldBeThrown() throws Exception {
        stubRepositoryToReturnExistingEmployee();
        try {
            employeeSvc.save(EmployeeUtil.createEmployee());
            fail("Expected exception");
        } catch (EmployeeExistsException ignored) {
        }
        verify(employeeRepo, never()).save(any(Employee.class));
    }

    private void stubRepositoryToReturnExistingEmployee() {
        final Employee employee = EmployeeUtil.createEmployee();
        when(employeeRepo.findOne(employee.getId())).thenReturn(employee);
    }

    @Test
    public void shouldListAllEmployees_GivenThereExistSome_ThenTheCollectionShouldBeReturned() throws Exception {
        stubRepositoryToReturnExistingEmployees(10);
        Collection<Employee> list = employeeSvc.getList();
        assertNotNull(list);
        assertEquals(10, list.size());
        verify(employeeRepo, times(1)).findAll();
    }

    private void stubRepositoryToReturnExistingEmployees(int howMany) {
        when(employeeRepo.findAll()).thenReturn(EmployeeUtil.createEmployeeList(howMany));
    }

    @Test
    public void shouldListAllEmployees_GivenThereNoneExist_ThenTheEmptyCollectionShouldBeReturned() throws Exception {
        stubRepositoryToReturnExistingEmployees(0);
        Collection<Employee> list = employeeSvc.getList();
        assertNotNull(list);
        assertTrue(list.isEmpty());
        verify(employeeRepo, times(1)).findAll();
    }

}
