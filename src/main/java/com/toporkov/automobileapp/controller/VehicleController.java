package com.toporkov.automobileapp.controller;

import com.toporkov.automobileapp.model.Vehicle;
import com.toporkov.automobileapp.service.VehicleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(final VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping
    public String getAll(final Model model) {
        final List<Vehicle> vehicles = vehicleService.findAll();
        model.addAttribute("vehicles", vehicles);

        return "vehicles/listAll";
    }

    @GetMapping("/{id}")
    public String getVehiclePage(@PathVariable("id") int id, Model model) {
        final Vehicle vehicle = vehicleService.getById(id);
        model.addAttribute("vehicle", vehicle);
        return "vehicles/vehiclePage";
    }

    @DeleteMapping("/{id}")
    public String deleteVehicle(@PathVariable("id") int id) {
        vehicleService.delete(id);
        return "redirect:/vehicles";
    }
}
