package com.toporkov.automobileapp.web.rest;

import com.toporkov.automobileapp.service.VehicleService;
import com.toporkov.automobileapp.web.dto.domain.vehicle.VehicleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/enterprise")
public class EnterpriseVehicleRestController {

    private final VehicleService vehicleService;

    public EnterpriseVehicleRestController(final VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping("/{enterpriseId}/vehicles")
    public Page<VehicleDTO> findAllByEnterprise(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") Boolean ascending,
            @PathVariable("enterpriseId") Integer enterpriseId
    ) {
        final Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        final Pageable pageable = PageRequest.of(page, size, sort);

        return vehicleService.findAllByEnterpriseId(pageable, enterpriseId);
    }
}
