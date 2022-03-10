package com.example.demo.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Car;
import com.example.demo.repository.CarRepository;

@RestController
class CarController {

  private final CarRepository repository;

  @Autowired
  public CarController(CarRepository repository) {
    this.repository = repository;
  }

  @GetMapping("/cars")
  List<Car> all(@RequestParam(required=false) Integer limit,@RequestParam(required=false) Integer offset) {
//	  String req;
//	  if(limit != null) {
//		  req = "SELECT * FROM cars LIMIT " + limit;
//		  if(offset != null) {
//			  req = "SELECT * FROM cars LIMIT " + offset + "," + limit;
//		  }
//	  }
	  return repository.findAll();
  }

  @PostMapping("/cars")
  ResponseEntity<Car> newCar(@Valid @RequestBody Car newCar) {
	Car car = repository.save(newCar);
    return ResponseEntity.created(URI.create("http://localhost:8080/cars/" + car.getId())).body(car);
  }

  
  @GetMapping("/cars/{id}")
  Car one(@PathVariable Long id) {
    
    return repository.findById(id)
      .orElseThrow(() -> new CarNotFoundException("Not found"));
  }

  @PutMapping("/cars/{id}")
  Car replaceCar(@RequestBody Car newCar, @PathVariable Long id) {
    
    return repository.findById(id)
      .map(car -> {
        car.setManufacturerName(newCar.getManufacturerName());
        car.setModel(newCar.getModel());
        car.setPrice(newCar.getPrice());
        return repository.save(car);
      })
      .orElseGet(() -> {
        newCar.setId(id);
        return repository.save(newCar);
      });
  }

  @DeleteMapping("/cars/{id}")
  ResponseEntity<?> deleteCar(@PathVariable Long id) {
    repository.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
