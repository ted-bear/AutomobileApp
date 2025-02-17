package com.toporkov.automobileapp.web.rest;

import com.toporkov.automobileapp.model.DriverAssignment;
import com.toporkov.automobileapp.service.DriverAssignmentService;
import com.toporkov.automobileapp.web.dto.DriverAssignmentDto;
import com.toporkov.automobileapp.web.dto.DriverAssignmentListDto;
import com.toporkov.automobileapp.web.mapper.DriverAssignmentMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/driverAssignments")
public class DriverAssignmentRestController {

    private final DriverAssignmentService driverAssignmentService;
    private final DriverAssignmentMapper driverAssignmentMapper;

    public DriverAssignmentRestController(final DriverAssignmentService driverAssignmentService,
                                          final DriverAssignmentMapper driverAssignmentMapper) {
        this.driverAssignmentService = driverAssignmentService;
        this.driverAssignmentMapper = driverAssignmentMapper;
    }

    @GetMapping
    public DriverAssignmentListDto getAll() {
        final List<DriverAssignment> driverAssignments = driverAssignmentService.getAll();
        final List<DriverAssignmentDto> dtos = driverAssignments.stream()
                .map(driverAssignmentMapper::mapEntityToDto)
                .toList();
        return new DriverAssignmentListDto(dtos);
    }
}
