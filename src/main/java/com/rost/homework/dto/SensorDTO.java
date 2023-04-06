package com.rost.homework.dto;

import jakarta.validation.constraints.Size;

public class SensorDTO {
    @Size(min = 3,max = 30,message = "Имя сенсора должно быть в диапазоне от 3х до 30 символов")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
