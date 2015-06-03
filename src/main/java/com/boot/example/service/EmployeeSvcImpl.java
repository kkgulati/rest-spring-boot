package com.boot.example.service;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.boot.example.domain.Employee;
import com.boot.example.repository.EmployeeRepo;
import com.boot.example.service.exception.EmployeeExistsException;

@Service
@Validated
public class EmployeeSvcImpl implements EmployeeSvc {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeSvcImpl.class);
    private final EmployeeRepo repository;

    @Inject
    public EmployeeSvcImpl(final EmployeeRepo repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Employee save(@NotNull @Valid final Employee employee) {
        LOGGER.debug("Creating {}", employee);
        Employee existing = repository.findOne(employee.getId());
        if (existing != null) {
            throw new EmployeeExistsException(
                    String.format("Employee exists with id=%s", employee.getId()));
        }
        return repository.save(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employee> getList() {
        LOGGER.debug("Retrieving the list of all users");
        return repository.findAll();
    }

}
