package com.toporkov.automobileapp.web.controller;

import com.toporkov.automobileapp.model.VehicleModel;
import com.toporkov.automobileapp.service.VehicleModelService;
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

    @GetMapping("/{id}")
    public String showVehicle(@PathVariable("id") int id, Model model) {
        final VehicleModel vehicleToShow = vehicleModelService.getById(id);
        model.addAttribute("vehicleModel", vehicleToShow);
        return "vehicleModels/vehicleModelPage";
    }

    @GetMapping("/{id}/edit")
    public String getEditVehicleModelPage(@PathVariable("id") int id, Model model) {
        final VehicleModel vehicleToShow = vehicleModelService.getById(id);
        model.addAttribute("vehicleModel", vehicleToShow);
        return "vehicleModels/editVehicleModelPage";
    }

    @GetMapping("/create")
    public String getCreateVehicleModelPage(@ModelAttribute("vehicleModel") VehicleModel vehicleModel) {

        return "vehicleModels/createVehicleModelPage";
    }

    @PatchMapping("/{id}")
    public String updateVehicleModel(@PathVariable("id") int id,
                                     @ModelAttribute("vehicleModel") @Valid VehicleModel vehicleModel,
                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "vehicleModels/editVehicleModelPage";
        }

        vehicleModelService.update(id, vehicleModel);

        return "redirect:/vehicleModels";
    }

    @PostMapping
    public String createVehicleModel(@ModelAttribute("vehicleModel") @Valid VehicleModel vehicleModel,
                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "vehicleModels/createVehicleModelPage";
        }

        vehicleModelService.save(vehicleModel);
        return "redirect:/vehicleModels";
    }

    @DeleteMapping("/{id}")
    public String deleteVehicleModel(@PathVariable("id") int id) {
        vehicleModelService.delete(id);
        return "redirect:/vehicles";
    }
}
