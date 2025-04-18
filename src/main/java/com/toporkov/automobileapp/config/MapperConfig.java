package com.toporkov.automobileapp.config;

import com.toporkov.automobileapp.model.Driver;
import com.toporkov.automobileapp.model.Enterprise;
import com.toporkov.automobileapp.model.Manager;
import com.toporkov.automobileapp.model.Vehicle;
import com.toporkov.automobileapp.model.VehicleCoordinate;
import com.toporkov.automobileapp.web.dto.domain.RegistrationManagerDTO;
import com.toporkov.automobileapp.web.dto.domain.VehicleCoordinateDTO;
import com.toporkov.automobileapp.web.dto.domain.driver.DriverDTO;
import com.toporkov.automobileapp.web.dto.domain.enterprise.EnterpriseDTO;
import com.toporkov.automobileapp.web.dto.domain.vehicle.VehicleCsvDTO;
import com.toporkov.automobileapp.web.dto.domain.vehicle.VehicleDTO;
import com.toporkov.automobileapp.web.mapper.converter.DriverAssignmentListToActiveVehicleIdConverter;
import com.toporkov.automobileapp.web.mapper.converter.DriverListToIdListConverter;
import com.toporkov.automobileapp.web.mapper.converter.EnterpriseIdListToEnterpriseSetConverter;
import com.toporkov.automobileapp.web.mapper.converter.EnterpriseIdToEnterpriseConverter;
import com.toporkov.automobileapp.web.mapper.converter.EnterpriseToEnterpriseIdConverter;
import com.toporkov.automobileapp.web.mapper.converter.PointToCoordinatesConverter;
import com.toporkov.automobileapp.web.mapper.converter.VehicleListToIdListConverter;
import com.toporkov.automobileapp.web.mapper.converter.VehicleModelIdToVehicleModelConverter;
import com.toporkov.automobileapp.web.mapper.converter.VehicleModelToVehicleModelIdConverter;
import com.toporkov.automobileapp.web.mapper.converter.VehicleToVehicleIdConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    private final VehicleModelIdToVehicleModelConverter vehicleModelIdToVehicleModelConverter;
    private final EnterpriseIdToEnterpriseConverter enterpriseIdToEnterpriseConverter;
    private final EnterpriseIdListToEnterpriseSetConverter enterpriseIdListToEnterpriseSetConverter;

    public MapperConfig(
        final VehicleModelIdToVehicleModelConverter vehicleModelIdToVehicleModelConverter,
        final EnterpriseIdToEnterpriseConverter enterpriseIdToEnterpriseConverter,
        final EnterpriseIdListToEnterpriseSetConverter enterpriseIdListToEnterpriseSetConverter
    ) {
        this.vehicleModelIdToVehicleModelConverter = vehicleModelIdToVehicleModelConverter;
        this.enterpriseIdToEnterpriseConverter = enterpriseIdToEnterpriseConverter;
        this.enterpriseIdListToEnterpriseSetConverter = enterpriseIdListToEnterpriseSetConverter;
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

        modelMapper.typeMap(Vehicle.class, VehicleDTO.class)
            .addMappings(new PropertyMap<>() {
                @Override
                protected void configure() {
                    using(new EnterpriseToEnterpriseIdConverter())
                        .map(source.getEnterprise(), destination.getEnterpriseId());
                }
            });

        modelMapper.typeMap(VehicleDTO.class, Vehicle.class)
            .addMappings(new PropertyMap<>() {
                @Override
                protected void configure() {
                    using(vehicleModelIdToVehicleModelConverter)
                        .map(source.getVehicleModelId(), destination.getVehicleModel());
                }
            });

        modelMapper.typeMap(VehicleDTO.class, Vehicle.class)
            .addMappings(new PropertyMap<>() {
                @Override
                protected void configure() {
                    using(enterpriseIdToEnterpriseConverter)
                        .map(source.getEnterpriseId(), destination.getEnterprise());
                }
            });

        modelMapper.typeMap(VehicleCsvDTO.class, Vehicle.class)
            .addMappings(new PropertyMap<>() {
                @Override
                protected void configure() {
                    using(vehicleModelIdToVehicleModelConverter)
                        .map(source.getVehicleModelId(), destination.getVehicleModel());
                }
            });

        modelMapper.typeMap(VehicleCsvDTO.class, Vehicle.class)
            .addMappings(new PropertyMap<>() {
                @Override
                protected void configure() {
                    using(enterpriseIdToEnterpriseConverter)
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

        modelMapper.typeMap(VehicleCoordinate.class, VehicleCoordinateDTO.class)
            .addMappings(new PropertyMap<>() {
                @Override
                protected void configure() {
                    using(new VehicleToVehicleIdConverter())
                        .map(source.getVehicle(), destination.getVehicleId());
                }
            });

        modelMapper.typeMap(VehicleCoordinate.class, VehicleCoordinateDTO.class)
            .addMappings(new PropertyMap<>() {
                @Override
                protected void configure() {
                    using(new PointToCoordinatesConverter())
                        .map(source.getPosition(), destination.getPosition());
                }
            });

        modelMapper.typeMap(RegistrationManagerDTO.class, Manager.class)
            .addMappings(new PropertyMap<>() {
                @Override
                protected void configure() {
                    using(enterpriseIdListToEnterpriseSetConverter)
                        .map(source.getEnterprises(), destination.getEnterprises());
                }
            });

        return modelMapper;
    }
}
