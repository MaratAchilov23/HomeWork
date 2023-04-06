package com.rost.homework.service;

import com.rost.homework.models.Measurement;
import com.rost.homework.repository.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementService {

    private final MeasurementRepository repository;
    private final SensorService sensorService;

    @Autowired
    public MeasurementService(MeasurementRepository repository, SensorService sensorService) {
        this.repository = repository;

        this.sensorService = sensorService;
    }
    @Transactional
    public void saveMeasurement(Measurement measurement) {
        enrichMeasurement(measurement);
        repository.save(measurement);
    }

    public List<Measurement>finAll(){
       return repository.findAll();
    }

    public List<Measurement>findIfRaining(){
        return repository.findMeasurementByRainingIsTrue();
    }




    private void enrichMeasurement(Measurement measurement){
        measurement.setSensor(sensorService.findByName(measurement.getSensor().getName()).get());
        measurement.setMeasurementDataTime(LocalDateTime.now());
    }
}
