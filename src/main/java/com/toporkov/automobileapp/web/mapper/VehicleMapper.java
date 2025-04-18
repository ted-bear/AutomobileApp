package com.toporkov.automobileapp.web.mapper;

import com.toporkov.automobileapp.model.Vehicle;
import com.toporkov.automobileapp.web.dto.domain.vehicle.VehicleCsvDTO;
import com.toporkov.automobileapp.web.dto.domain.vehicle.VehicleDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import static com.toporkov.automobileapp.util.DateTimeUtil.toEnterpriseTime;

@Component
public class VehicleMapper {

    private final ModelMapper modelMapper;

    public VehicleMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Vehicle mapDtoToEntity(VehicleDTO vehicleDto) {
        return modelMapper.map(vehicleDto, Vehicle.class);
    }

    public Vehicle mapCsvDtoToEntity(VehicleCsvDTO vehicleDto) {
        return modelMapper.map(vehicleDto, Vehicle.class);
    }

    public VehicleDTO mapEntityToDto(Vehicle vehicle) {
        final VehicleDTO dto = modelMapper.map(vehicle, VehicleDTO.class);
        dto.setPurchaseDate(
            toEnterpriseTime(
                vehicle.getPurchaseDate(),
                vehicle.getEnterprise().getTimezone()
            )
        );
        return dto;
    }
}
