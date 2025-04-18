package com.toporkov.automobileapp.util.validator;

import java.util.Optional;

import com.toporkov.automobileapp.model.Vehicle;
import com.toporkov.automobileapp.service.VehicleService;
import com.toporkov.automobileapp.web.dto.domain.vehicle.VehicleDTO;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class VehicleValidator implements Validator {

    private final VehicleService vehicleService;

    public VehicleValidator(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return Vehicle.class.equals(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        final VehicleDTO vehicleToValidate = (VehicleDTO) target;
        final Optional<Vehicle> vehicleByNumber = vehicleService.getByNumber(vehicleToValidate.getNumber());

        boolean vehicleExists = vehicleByNumber.isPresent() &&
            vehicleByNumber.get().getId() != vehicleToValidate.getId();

        if (vehicleExists) {
            errors.rejectValue("number", "", "Машина с таким номер уже существует");
        }
    }
}
