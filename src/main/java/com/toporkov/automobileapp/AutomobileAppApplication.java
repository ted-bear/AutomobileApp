package com.toporkov.automobileapp;

import com.toporkov.automobileapp.model.Driver;
import com.toporkov.automobileapp.model.Enterprise;
import com.toporkov.automobileapp.model.Vehicle;
import com.toporkov.automobileapp.web.dto.DriverDto;
import com.toporkov.automobileapp.web.dto.EnterpriseDto;
import com.toporkov.automobileapp.web.dto.VehicleDto;
import com.toporkov.automobileapp.web.mapper.converter.DriverAssignmentListToActiveVehicleIdConverter;
import com.toporkov.automobileapp.web.mapper.converter.DriverListToIdListConverter;
import com.toporkov.automobileapp.web.mapper.converter.VehicleListToIdListConverter;
import com.toporkov.automobileapp.web.mapper.converter.VehicleModelToVehicleModelIdConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AutomobileAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutomobileAppApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.typeMap(Enterprise.class, EnterpriseDto.class)
                .addMappings(new PropertyMap<>() {
                    @Override
                    protected void configure() {
                        using(new VehicleListToIdListConverter())
                                .map(source.getVehicles(), destination.getVehicles());
                    }
                })
                .addMappings(new PropertyMap<>() {
                    @Override
                    protected void configure() {
                        using(new DriverListToIdListConverter())
                                .map(source.getDrivers(), destination.getDrivers());
                    }
                });

        modelMapper.typeMap(Vehicle.class, VehicleDto.class)
                .addMappings(new PropertyMap<>() {
                    @Override
                    protected void configure() {
                        using(new VehicleModelToVehicleModelIdConverter())
                                .map(source.getVehicleModel(), destination.getVehicleModelId());
                    }
                });

        modelMapper.typeMap(Driver.class, DriverDto.class)
                .addMappings(new PropertyMap<>() {
                    @Override
                    protected void configure() {
                        using(new DriverAssignmentListToActiveVehicleIdConverter())
                                .map(source.getDriverAssignments(), destination.getActiveVehicleId());
                    }
                });

        return modelMapper;
    }

}
