package com.rost.homework.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "measurement")
public class Measurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "value")
    @NotNull
    @Min(value = -100)
    @Max(value = 100)
    private Double value;
    @Column(name = "raining")
   @NotNull
    private Boolean raining;
    @NotNull
    @Column(name = "measurement_data_time")
    private LocalDateTime measurementDataTime;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "sensor", referencedColumnName = "name")
    private Sensor sensor;

    public Measurement() {
    }

    public Measurement(Double value, Boolean raining) {
        this.value = value;
        this.raining = raining;
    }

    public Measurement(Double value, Boolean raining, Sensor sensor) {
        this.value = value;
        this.raining = raining;
        this.sensor = sensor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Boolean isRaining() {
        return raining;
    }

    public void setRaining(Boolean raining) {
        this.raining = raining;
    }

    public LocalDateTime getMeasurementDataTime() {
        return measurementDataTime;
    }

    public void setMeasurementDataTime(LocalDateTime measurementDataTime) {
        this.measurementDataTime = measurementDataTime;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    @Override
    public String toString() {
        return "Measurement{" +
                "id=" + id +
                ", value=" + value +
                ", raining=" + raining +
                ", measurementDataTime=" + measurementDataTime +
                '}';
    }
}
