package com.rost.homework.service;

import com.rost.homework.models.Sensor;
import com.rost.homework.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorService {

    private final SensorRepository repository;
    @Autowired
    public SensorService(SensorRepository repository) {
        this.repository = repository;
    }
    @Transactional
    public void saveSensor(Sensor sensor){
        repository.save(sensor);
    }

    public Optional<Sensor> findByName(String name) {
        return repository.findByName(name);
    }
    public List<Sensor>findAll(){
        return  repository.findAll();
    }
}

