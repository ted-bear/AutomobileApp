package com.toporkov.automobileapp.service;

import java.util.List;

import com.toporkov.automobileapp.model.Manager;
import com.toporkov.automobileapp.model.Role;
import com.toporkov.automobileapp.repository.ManagerRepository;
import com.toporkov.automobileapp.util.SecurityUtil;
import com.toporkov.automobileapp.util.exception.UserNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ManagerService {

    private final ManagerRepository managerRepository;
    private final PasswordEncoder passwordEncoder;

    public ManagerService(
        final ManagerRepository managerRepository,
        final PasswordEncoder passwordEncoder
    ) {
        this.managerRepository = managerRepository;
        this.passwordEncoder = passwordEncoder;
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

    public Manager getCurrentManager() {
        final Manager manager = SecurityUtil.getCurrentManager();
        return managerRepository.findById(manager.getId()).orElseThrow(UserNotFoundException::new);
    }

    @Transactional
    public void create(Manager manager) {

        if (managerRepository.findByUsername(manager.getUsername()).isPresent()) {
            throw new IllegalStateException("Manager already exists");
        }

        manager.getEnterprises().forEach(enterprise -> enterprise.addManager(manager));
        manager.setPassword(passwordEncoder.encode(manager.getPassword()));
        manager.setRole(Role.ROLE_MANAGER);

        managerRepository.save(manager);
    }
}
