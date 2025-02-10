package com.toporkov.automobileapp.controller;

import com.toporkov.automobileapp.model.VehicleModel;
import com.toporkov.automobileapp.service.VehicleModelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/vehicleModels")
public class VehicleModelController {

    private final VehicleModelService vehicleModelService;

    public VehicleModelController(VehicleModelService vehicleModelService) {
        this.vehicleModelService = vehicleModelService;
    }

    @GetMapping
    public String getAllModels(Model model) {
        List<VehicleModel> vehicleModels = vehicleModelService.findAll();
        model.addAttribute("vehicleModels", vehicleModels);
        return "vehicleModels/listAll";
    }
}
