package com.toporkov.automobileapp.config;

import com.toporkov.automobileapp.model.Driver;
import com.toporkov.automobileapp.model.Enterprise;
import com.toporkov.automobileapp.model.Manager;
import com.toporkov.automobileapp.model.Vehicle;
import com.toporkov.automobileapp.service.EnterpriseService;
import com.toporkov.automobileapp.service.VehicleModelService;
import com.toporkov.automobileapp.web.dto.DriverDTO;
import com.toporkov.automobileapp.web.dto.RegistrationManagerDTO;
import com.toporkov.automobileapp.web.dto.domain.enterprise.EnterpriseDTO;
import com.toporkov.automobileapp.web.dto.domain.vehicle.VehicleDTO;
import com.toporkov.automobileapp.web.mapper.converter.DriverAssignmentListToActiveVehicleIdConverter;
import com.toporkov.automobileapp.web.mapper.converter.DriverListToIdListConverter;
import com.toporkov.automobileapp.web.mapper.converter.EnterpriseIdListToEnterpriseSetConverter;
import com.toporkov.automobileapp.web.mapper.converter.EnterpriseIdToEnterpriseConverter;
import com.toporkov.automobileapp.web.mapper.converter.VehicleListToIdListConverter;
import com.toporkov.automobileapp.web.mapper.converter.VehicleModelIdToVehicleModelConverter;
import com.toporkov.automobileapp.web.mapper.converter.VehicleModelToVehicleModelIdConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    private final EnterpriseService enterpriseService;
    private final VehicleModelService vehicleModelService;

    public MapperConfig(EnterpriseService enterpriseService,
                        VehicleModelService vehicleModelService) {
        this.enterpriseService = enterpriseService;
        this.vehicleModelService = vehicleModelService;
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.typeMap(Enterprise.class, EnterpriseDTO.class)
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

        modelMapper.typeMap(Vehicle.class, VehicleDTO.class)
                .addMappings(new PropertyMap<>() {
                    @Override
                    protected void configure() {
                        using(new VehicleModelToVehicleModelIdConverter())
                                .map(source.getVehicleModel(), destination.getVehicleModelId());
                    }
                });

        modelMapper.typeMap(VehicleDTO.class, Vehicle.class)
                .addMappings(new PropertyMap<>() {
                    @Override
                    protected void configure() {
                        using(new VehicleModelIdToVehicleModelConverter(vehicleModelService))
                                .map(source.getVehicleModelId(), destination.getVehicleModel());
                    }
                });

        modelMapper.typeMap(VehicleDTO.class, Vehicle.class)
                .addMappings(new PropertyMap<>() {
                    @Override
                    protected void configure() {
                        using(new EnterpriseIdToEnterpriseConverter(enterpriseService))
                                .map(source.getEnterpriseId(), destination.getEnterprise());
                    }
                });

        modelMapper.typeMap(Driver.class, DriverDTO.class)
                .addMappings(new PropertyMap<>() {
                    @Override
                    protected void configure() {
                        using(new DriverAssignmentListToActiveVehicleIdConverter())
                                .map(source.getDriverAssignments(), destination.getActiveVehicleId());
                    }
                });

        modelMapper.typeMap(RegistrationManagerDTO.class, Manager.class)
                .addMappings(new PropertyMap<>() {
                    @Override
                    protected void configure() {
                        using(new EnterpriseIdListToEnterpriseSetConverter(enterpriseService))
                                .map(source.getEnterprises(), destination.getEnterprises());
                    }
                });

        return modelMapper;
    }
}
