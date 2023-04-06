package com.rost.homework.repository;

import com.rost.homework.models.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SensorRepository extends JpaRepository<Sensor,Integer> {
    public Optional<Sensor>findByName(String name);

}
