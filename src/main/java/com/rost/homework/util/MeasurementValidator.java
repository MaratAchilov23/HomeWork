package com.rost.homework.util;

import com.rost.homework.models.Measurement;
import com.rost.homework.service.MeasurementService;
import com.rost.homework.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Component
public class MeasurementValidator implements Validator {
    private final SensorService sensorService;
    private final MeasurementService service;
    @Autowired
    public MeasurementValidator(SensorService sensorService, MeasurementService service) {
        this.sensorService = sensorService;
        this.service = service;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Measurement.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
    Measurement measurement = (Measurement) target;
    if(measurement.getSensor()==null){
        return;
    }
    if(sensorService.findByName(measurement.getSensor().getName()).isEmpty())
        errors.rejectValue("sensor","Нет сенсора с таким именем");
    }
}
