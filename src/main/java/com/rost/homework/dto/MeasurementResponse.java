package com.rost.homework.dto;

import java.util.List;

public class MeasurementResponse {
    private  List<MeasurementDTO> measurements;

    public MeasurementResponse(List<MeasurementDTO> measurements) {
        this.measurements = measurements;
    }

    public List<MeasurementDTO> getMeasurements() {
        return measurements;
    }

    public void setMeasurementDTOList(List<MeasurementDTO> measurements) {
        this.measurements = measurements;
    }
}
