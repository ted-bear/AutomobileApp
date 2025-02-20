package com.toporkov.automobileapp.service;

import com.toporkov.automobileapp.model.Manager;
import com.toporkov.automobileapp.model.Role;
import com.toporkov.automobileapp.repository.ManagerRepository;
import com.toporkov.automobileapp.util.exception.UserNotFoundException;
import com.toporkov.automobileapp.web.dto.RegistrationManagerDTO;
import com.toporkov.automobileapp.web.mapper.ManagerMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ManagerService {

    private final ManagerRepository managerRepository;
    private final ManagerMapper managerMapper;
    private final PasswordEncoder passwordEncoder;
    private final EnterpriseService enterpriseService;

    public ManagerService(final ManagerRepository managerRepository,
                          final ManagerMapper managerMapper,
                          final PasswordEncoder passwordEncoder,
                          final EnterpriseService enterpriseService) {
        this.managerRepository = managerRepository;
        this.managerMapper = managerMapper;
        this.passwordEncoder = passwordEncoder;
        this.enterpriseService = enterpriseService;
    }

    public List<Manager> findAll() {
        return managerRepository.findAll();
    }

    public Manager getById(Integer id) {
        return managerRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public Manager getByUsername(String username) {
        return managerRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
    }

    @Transactional
    public void create(RegistrationManagerDTO registrationManagerDTO) {
        final Manager manager = managerMapper.mapDtoToEntity(registrationManagerDTO);

        if (managerRepository.findByUsername(manager.getUsername()).isPresent()) {
            throw new IllegalStateException("Manager already exists");
        }

        if (!registrationManagerDTO.getPassword().equals(registrationManagerDTO.getPasswordConfirmation())) {
            throw new IllegalStateException("Password and password confirmation do not match");
        }

        manager.getEnterprises().forEach(enterprise -> enterprise.addManager(manager));
        manager.setPassword(passwordEncoder.encode(manager.getPassword()));
        manager.setRole(Role.ROLE_MANAGER);

        managerRepository.save(manager);
    }
}
