package com.rost.homework.service;

import com.rost.homework.models.Measurement;
import com.rost.homework.models.Sensor;
import com.rost.homework.repository.MeasurementRepository;
import com.rost.homework.util.MeasurementNotAddedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    private void enrichMeasurement(Measurement measurement){
        String sensorName = measurement.getSensor().getName();
        LocalDateTime measurementDataTime = measurement.getMeasurementDataTime();

        Optional<Measurement> existingMeasurement = repository.findBySensorNameAndMeasurementDataTime(sensorName, measurementDataTime);
        if (existingMeasurement.isPresent()) {
            throw new MeasurementNotAddedException("Measurement with sensor " + sensorName + " and measurement time " + measurementDataTime + " already exists");
        }

        measurement.setSensor(sensorService.findByName(sensorName).get());
        measurement.setMeasurementDataTime(LocalDateTime.now());
    }

    /*  private void enrichMeasurement(Measurement measurement){
        measurement.setSensor(sensorService.findByName(measurement.getSensor().getName()).get());
        measurement.setMeasurementDataTime(LocalDateTime.now());
    }*/
    public boolean existsBySensorAndMeasurementDataTime(Sensor sensor, LocalDateTime dateTime) {
        return repository.existsBySensorAndMeasurementDataTime(sensor, dateTime);
    }
    public List<Measurement>findAll(){
        return repository.findAll();
    }

    public List<Measurement>findIfRaining(){
        return repository.findMeasurementByRainingIsTrue();
    }
}
