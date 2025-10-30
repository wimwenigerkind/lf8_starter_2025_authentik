package de.szut.lf8_starter.employee;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class EmployeeClient {

    private final RestTemplate restTemplate;
    private final String employeeServiceUrl;

    public EmployeeClient(
            RestTemplate restTemplate,
            @Value("${employee.service.url:https://employee-api.szut.dev}") String employeeServiceUrl) {
        this.restTemplate = restTemplate;
        this.employeeServiceUrl = employeeServiceUrl;
    }

    /**
     * Checks if an employee exists
     * @param employeeId the employee ID
     * @return true if employee exists, false otherwise
     */
    public boolean employeeExists(Long employeeId) {
        try {
            String url = employeeServiceUrl + "/employees/" + employeeId;
            restTemplate.getForObject(url, EmployeeDto.class);
            return true;
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return false;
            }
            throw e;
        }
    }

    /**
     * Checks if an employee has any qualification
     * @param employeeId the employee ID
     * @return true if employee has at least one skill in skillSet
     */
    public boolean employeeHasQualification(Long employeeId) {
        try {
            String url = employeeServiceUrl + "/employees/" + employeeId;
            EmployeeDto employee = restTemplate.getForObject(url, EmployeeDto.class);

            return employee != null
                && employee.getSkillSet() != null
                && !employee.getSkillSet().isEmpty();
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return false;
            }
            throw e;
        }
    }
}