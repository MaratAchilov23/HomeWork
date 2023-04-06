package com.rost.homework.controllers;

import com.rost.homework.dto.MeasurementDTO;
import com.rost.homework.dto.MeasurementResponse;
import com.rost.homework.dto.SensorDTO;
import com.rost.homework.models.Measurement;
import com.rost.homework.models.Sensor;
import com.rost.homework.service.MeasurementService;
import com.rost.homework.service.SensorService;
import com.rost.homework.util.MeasurementNotAddedException;
import com.rost.homework.util.MeasurementResponseError;
import com.rost.homework.util.MeasurementValidator;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {

    private final MeasurementService service;
    private final SensorService sensorService;

    private final MeasurementValidator validator;

    private final ModelMapper modelMapper;
    @Autowired
    public MeasurementController(MeasurementService service, SensorService sensorService, MeasurementValidator validator, ModelMapper modelMapper) {
        this.service = service;
        this.sensorService = sensorService;
        this.validator = validator;
        this.modelMapper = modelMapper;
    }

   @PostMapping("/add")
    public ResponseEntity<HttpStatus>creat(@RequestBody @Valid MeasurementDTO measurementDTO,
                                           BindingResult bindingResult){
        Measurement measurement = convertToMeasurement(measurementDTO);
        String sensorName = measurement.getSensor().getName();
        System.out.println(sensorName);
        validator.validate(measurement,bindingResult);
        if (bindingResult.hasErrors()){
            StringBuilder errMsg = new StringBuilder();
            List<FieldError> errorList= bindingResult.getFieldErrors();
            for (FieldError e:errorList){
                errMsg.append(e.getField())
                        .append("-")
                        .append(e.getDefaultMessage())
                        .append(";");
            }
            throw new MeasurementNotAddedException(errMsg.toString());
        }
        service.saveMeasurement(measurement);

        return new ResponseEntity<>(HttpStatus.OK);
    }

  /*  @PostMapping("/add")
    public ResponseEntity<HttpStatus> creat(@RequestBody @Valid MeasurementDTO measurementDTO,
                                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errMsg = new StringBuilder();
            List<FieldError> errorList = bindingResult.getFieldErrors();
            for (FieldError e : errorList) {
                errMsg.append(e.getField())
                        .append("-")
                        .append(e.getDefaultMessage())
                        .append(";");
            }
            throw new MeasurementNotAddedException(errMsg.toString());
        }

        // Поиск или создание объекта Sensor по имени из запроса
        Sensor sensor = convertToSensor();
        if (sensor==null) {
            throw new SensorNotFoundException();
        }
        // Создание объекта Measurement с назначенным Sensor и сохранение в базу данных
        Measurement measurement = convertToMeasurement(measurementDTO);
        measurement.setSensor(sensor);
        service.saveMeasurement(measurement);

        return new ResponseEntity<>(HttpStatus.OK);
    }*/

    @GetMapping("/rainyDaysCount")
    public Long countOfReining(){
      return   service.findAll().stream().filter(Measurement::isRaining).count();
    }



    @GetMapping()
    public MeasurementResponse all(){
       return new MeasurementResponse(service.findAll().stream().
               map(this::convertToMeasurementDTO).collect(Collectors.toList()));
    }

  @ExceptionHandler
  private ResponseEntity<MeasurementResponseError>handle(MeasurementNotAddedException e) {
      MeasurementResponseError error = new MeasurementResponseError(System.currentTimeMillis(),
              "Показания не были учтены");

      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }


    public Measurement convertToMeasurement(MeasurementDTO measurementDTO){
        return modelMapper.map(measurementDTO, Measurement.class);
    }

    public MeasurementDTO convertToMeasurementDTO(Measurement measurement){
        return modelMapper.map(measurement, MeasurementDTO.class);

    }
    public SensorDTO convertToSensorDTO(Sensor sensor){
        return modelMapper.map(sensor,SensorDTO.class);
    }
    public Sensor convertToSensor(Optional<Sensor> sensorDTO){
        return modelMapper.map(sensorDTO, Sensor.class);
    }
}
