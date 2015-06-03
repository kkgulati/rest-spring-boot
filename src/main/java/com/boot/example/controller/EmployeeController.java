package com.boot.example.controller;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.boot.example.domain.Employee;
import com.boot.example.service.EmployeeSvc;
import com.boot.example.service.exception.EmployeeExistsException;

@RestController
public class EmployeeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);
    private final EmployeeSvc employeeSvc;

    @Inject
    public EmployeeController(final EmployeeSvc employeeSvc) {
        this.employeeSvc = employeeSvc;
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public Employee createEmployee(@RequestBody @Valid final Employee employee) {
        LOGGER.debug("Received request to create the {}", employee);
        return employeeSvc.save(employee);
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public List<Employee> listEmployees() {
        LOGGER.debug("Received request to list all users");
        return employeeSvc.getList();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleEmployeeAlreadyExistsException(EmployeeExistsException e) {
        return e.getMessage();
    }

}
