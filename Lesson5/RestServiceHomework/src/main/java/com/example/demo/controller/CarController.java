package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
  List<Car> all() {
    return repository.findAll();
  }

  @PostMapping("/cars")
  Car newCar(@RequestBody Car newCar) {
    return repository.save(newCar);
  }

  
  @GetMapping("/cars/{id}")
  Car one(@PathVariable Long id) {
    
    return repository.findById(id)
      .orElseThrow(() -> new CarNotFoundException());
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
  void deleteCar(@PathVariable Long id) {
    repository.deleteById(id);
  }
}
