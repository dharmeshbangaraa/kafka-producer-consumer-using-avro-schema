package com.kafka.payroll.service.serviceImpl;

import com.kafka.payroll.entity.Payroll;
import com.kafka.payroll.repository.PayrollRepository;
import com.kafka.payroll.service.PayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayrollServiceImpl implements PayrollService {

    @Autowired
    private PayrollRepository payrollRepository;

    @Override
    public Payroll addNewPayroll(Payroll payroll) {
        return this.payrollRepository.save(payroll);
    }
}
