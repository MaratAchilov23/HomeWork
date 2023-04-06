package com.rost.homework.repository;

import com.rost.homework.models.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeasurementRepository  extends JpaRepository<Measurement,Integer> {

    List<Measurement>findMeasurementByRainingIsTrue();
}
