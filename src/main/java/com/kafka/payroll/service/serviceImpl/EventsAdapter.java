package com.kafka.payroll.service.serviceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka.employee.entity.Employee;
import com.kafka.payroll.entity.Payroll;
import com.kafka.payroll.service.PayrollService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EventsAdapter {

    @Autowired
    private PayrollService payrollService;

    private final ObjectMapper jsonObjectMapper = new ObjectMapper();

    @KafkaListener(topics = {"${kafka.topic-name}"}, groupId = "${kafka.consumer.group-id}")
    public void consumeEvents(ConsumerRecord<String, Object> employeeEvent) throws JsonProcessingException {
        Employee empObj = jsonObjectMapper.readValue(employeeEvent.value().toString(), Employee.class);
        var emp = payrollService.addNewPayroll(mapToPayroll(empObj));
        log.info("Employee event consumed: {}", emp);
    }

    private Payroll mapToPayroll(Employee emp) {
        return Payroll
                .builder()
                .employeeId(emp.getEmployeeId())
                .employeeName(emp.getEmployeeName())
                .salary(emp.getSalary())
                .isPermanent(emp.getIsPermanent())
                .isPFActive(emp.getIsPFActive())
                .build();
    }
}
