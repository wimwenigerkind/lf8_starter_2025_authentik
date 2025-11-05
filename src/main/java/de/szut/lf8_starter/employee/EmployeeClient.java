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
     * Checks if an employee has the required qualifications
     * @param employeeId the employee ID
     * @param requiredQualificationIds the list of required qualification IDs (can be null or empty)
     * @return true if employee has all required qualifications, or if no qualifications are required
     */
    public boolean employeeHasQualification(Long employeeId, java.util.List<Long> requiredQualificationIds) {
        try {
            String url = employeeServiceUrl + "/employees/" + employeeId;
            EmployeeDto employee = restTemplate.getForObject(url, EmployeeDto.class);

            if (employee == null || employee.getSkillSet() == null || employee.getSkillSet().isEmpty()) {
                return false;
            }

            if (requiredQualificationIds == null || requiredQualificationIds.isEmpty()) {
                return true;
            }

            java.util.Set<Long> employeeQualificationIds = employee.getSkillSet().stream()
                .map(QualificationDto::getId)
                .collect(java.util.stream.Collectors.toSet());

            return employeeQualificationIds.containsAll(requiredQualificationIds);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return false;
            }
            throw e;
        }
    }

    public boolean isValidQualification(String qualificationId) {
        try {
            String url = employeeServiceUrl + "/qualifications";
            QualificationDto[] qualifications = restTemplate.getForObject(url, QualificationDto[].class);
            if (qualifications == null) {
                return false;
            }
            for (QualificationDto q : qualifications) {
                if (q != null && qualificationId.equals(String.valueOf(q.getId()))) {
                    return true;
                }
            }
            return false;
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return false;
            }
            throw e;
        }
    }
}