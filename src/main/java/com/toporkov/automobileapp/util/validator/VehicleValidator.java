package com.toporkov.automobileapp.util.validator;

import com.toporkov.automobileapp.model.Vehicle;
import com.toporkov.automobileapp.service.VehicleService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class VehicleValidator implements Validator {

    private final VehicleService vehicleService;

    public VehicleValidator(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Vehicle.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        final Vehicle vehicleToValidate = (Vehicle) target;
        final Optional<Vehicle> vehicleByNumber = vehicleService.getByNumber(vehicleToValidate.getNumber());

        boolean vehicleExists = vehicleByNumber.isPresent() &&
                vehicleByNumber.get().getId() != vehicleToValidate.getId();

        if (vehicleExists) {
            errors.rejectValue("number", "", "Машина с таким номер уже существует");
        }
    }
}
