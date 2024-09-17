package com.kafka.employee.service.serviceImpl;

import com.kafka.employee.entity.Employee;
import com.kafka.employee.repository.EmployeeRepository;
import com.kafka.employee.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    @Value("${kafka.topic-name}")
    private String topicName;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public Employee addEmployee(Employee employee) {
        this.employeeRepository.save(employee);
        log.info("Employee added: {}", employee);
        kafkaTemplate.send(topicName, employee);
        log.info("Employee data sent to Kafka topic: {}", topicName);
        return employee;
    }
}
