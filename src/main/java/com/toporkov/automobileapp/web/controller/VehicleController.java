package com.toporkov.automobileapp.web.controller;

import com.toporkov.automobileapp.model.Condition;
import com.toporkov.automobileapp.model.Vehicle;
import com.toporkov.automobileapp.model.VehicleModel;
import com.toporkov.automobileapp.service.VehicleModelService;
import com.toporkov.automobileapp.service.VehicleService;
import com.toporkov.automobileapp.util.validator.VehicleValidator;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;
    private final VehicleModelService vehicleModelService;
    private final VehicleValidator vehicleValidator;

    public VehicleController(final VehicleService vehicleService,
                             VehicleModelService vehicleModelService,
                             VehicleValidator vehicleValidator) {
        this.vehicleService = vehicleService;
        this.vehicleModelService = vehicleModelService;
        this.vehicleValidator = vehicleValidator;
    }

    @GetMapping
    public String getAll(final Model model) {
        final List<Vehicle> vehicles = vehicleService.findAllActive();
        model.addAttribute("vehicles", vehicles);

        return "vehicles/listAll";
    }

    @GetMapping("/{id}")
    public String showVehiclePage(@PathVariable("id") int id, Model model) {
        final Vehicle vehicle = vehicleService.getById(id);
        model.addAttribute("vehicle", vehicle);
        return "vehicles/vehiclePage";
    }

    @GetMapping("/{id}/edit")
    public String getVehicleEditPage(@PathVariable("id") int id,
                                     @ModelAttribute("vehicleModel") VehicleModel vehicleModel,
                                     Model model) {
        final Vehicle vehicle = vehicleService.getById(id);
        final List<VehicleModel> vehicleModels = vehicleModelService.findAll();
        final List<Condition> conditions = Condition.getAsList();

        model.addAttribute("vehicle", vehicle);
        model.addAttribute("vehicleModels", vehicleModels);
        model.addAttribute("conditions", conditions);

        return "vehicles/vehicleEditPage";
    }

    @GetMapping("/create")
    public String getVehicleCreatePage(@ModelAttribute("vehicle") Vehicle vehicle,
                                       Model model) {
        final List<VehicleModel> vehicleModels = vehicleModelService.findAll();
        final List<Condition> conditions = Condition.getAsList();

        model.addAttribute("vehicleModels", vehicleModels);
        model.addAttribute("conditions", conditions);

        return "vehicles/vehicleCreatePage";
    }

    @PostMapping
    public String createVehicle(@ModelAttribute("vehicle") @Valid Vehicle vehicle,
                                BindingResult bindingResult,
                                Model model) {
        vehicleValidator.validate(vehicle, bindingResult);

        if (bindingResult.hasErrors()) {
            final List<VehicleModel> vehicleModels = vehicleModelService.findAll();
            final List<Condition> conditions = Condition.getAsList();

            model.addAttribute("vehicleModels", vehicleModels);
            model.addAttribute("conditions", conditions);

            return "vehicles/vehicleCreatePage";
        }

        vehicleService.save(vehicle);
        return "redirect:/vehicles";
    }

    @PatchMapping("/{id}")
    public String updateVehicle(@PathVariable("id") int id,
                                @ModelAttribute("vehicle") @Valid Vehicle vehicle,
                                BindingResult bindingResult) {
        vehicleValidator.validate(vehicle, bindingResult);

        if (bindingResult.hasErrors()) {
            return "vehicles/vehicleEditPage";
        }

        vehicleService.update(id, vehicle);
        return "redirect:/vehicles";
    }

    @DeleteMapping("/{id}")
    public String deleteVehicle(@PathVariable("id") int id) {
        vehicleService.delete(id);
        return "redirect:/vehicles";
    }
}
