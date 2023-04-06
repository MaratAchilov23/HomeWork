package com.rost.homework.repository;

import com.rost.homework.models.Measurement;
import com.rost.homework.models.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MeasurementRepository  extends JpaRepository<Measurement,Integer> {

    List<Measurement>findMeasurementByRainingIsTrue();
    boolean existsBySensorAndMeasurementDataTime(Sensor sensor, LocalDateTime dateTime);
  Optional<Measurement>findBySensorNameAndMeasurementDataTime(String name, LocalDateTime time);
}
