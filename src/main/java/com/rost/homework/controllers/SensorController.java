package com.rost.homework.controllers;

import com.rost.homework.dto.SensorDTO;
import com.rost.homework.models.Sensor;
import com.rost.homework.service.SensorService;
import com.rost.homework.util.SensorNotCreatedException;
import com.rost.homework.util.SensorNotFoundException;
import com.rost.homework.util.SensorResponseError;
import com.rost.homework.util.SensorValidator;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sensors")
public class SensorController {
    private final SensorService sensorService;

    private final SensorValidator validator;

    private final ModelMapper modelMapper;
    @Autowired
    public SensorController(SensorService sensorService, SensorValidator validator, ModelMapper modelMapper) {
        this.sensorService = sensorService;
        this.validator = validator;
        this.modelMapper = modelMapper;
    }
    @PostMapping("/registration")
    public ResponseEntity<HttpStatus>create(@RequestBody @Valid SensorDTO sensorDTO, BindingResult bindingResult){
        Sensor sensor = convertToSensor(sensorDTO);
        validator.validate(sensor,bindingResult);
        if(bindingResult.hasErrors()){
            StringBuilder builder = new StringBuilder();
            List<FieldError>errors= bindingResult.getFieldErrors();
            for(FieldError field:errors){
                builder.append(field.getField())
                        .append("-")
                        .append(field.getDefaultMessage())
                        .append(";");

            }
            throw new SensorNotCreatedException(builder.toString());
        }
        sensorService.saveSensor(sensor);
     return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/all")
    public List<SensorDTO> all(){
        return sensorService.findAll().stream().map(this::convertToSensorDTO).collect(Collectors.toList());
    }


   @ExceptionHandler
   private ResponseEntity<SensorResponseError>handleError(SensorNotCreatedException e){
          SensorResponseError sensorResponseError = new SensorResponseError("сенсор уже существует ",
                  System.currentTimeMillis());
          return new ResponseEntity<>(sensorResponseError,HttpStatus.BAD_REQUEST);
   }

   @ExceptionHandler
   private ResponseEntity<SensorResponseError>handleError(SensorNotFoundException e){
        SensorResponseError sensorResponseError = new SensorResponseError("сенсор с таким именем не найден",
                System.currentTimeMillis());
        return new ResponseEntity<>(sensorResponseError,HttpStatus.NOT_FOUND);

   }




    public Sensor convertToSensor(SensorDTO sensorDTO){
        return modelMapper.map(sensorDTO, Sensor.class);
    }

    public SensorDTO convertToSensorDTO(Sensor sensor){
        return modelMapper.map(sensor,SensorDTO.class);
    }

}
