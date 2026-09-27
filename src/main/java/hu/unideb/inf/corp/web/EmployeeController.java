package hu.unideb.inf.corp.web;

import hu.unideb.inf.corp.model.Employee;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface EmployeeController {
    @RequestMapping(
            path = "/api/employee/{employeeId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    Employee getOne(@PathVariable String employeeId);

    @GetMapping("/api/employee")
    List<Employee> getAll();

    @PostMapping("/api/employee")
    Employee createOne(@RequestBody Employee employee);

    @PutMapping("/api/employee")
    Employee updateOne(@RequestBody Employee employee);

    @DeleteMapping("/api/employee/{employeeId}")
    void deleteOne(@PathVariable String employeeId);
}
