package com.rost.homework;

import com.rost.homework.dto.SensorDTO;
import com.rost.homework.models.Measurement;
import com.rost.homework.models.Sensor;
import com.rost.homework.service.MeasurementService;
import com.rost.homework.service.SensorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HomeWorkApplication {





    public static void main(String[] args) {

        SpringApplication.run(HomeWorkApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
