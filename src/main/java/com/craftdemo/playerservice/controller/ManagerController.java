package com.craftdemo.playerservice.controller;

import com.craftdemo.playerservice.model.Manager;
import com.craftdemo.playerservice.repository.ManagerRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/managers")
public class ManagerController {

    private final ManagerRepository managerRepository;

    public ManagerController(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    @PostMapping
    public Manager createManager(@RequestBody Manager manager) {
        return managerRepository.save(manager);
    }

    @PutMapping("/{id}")
    public Manager updateManager(@PathVariable Long id, @RequestBody Manager updatedManager) {
        Manager existing = managerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Manager not found"));

        existing.setName(updatedManager.getName());
        existing.setNationality(updatedManager.getNationality());

        return managerRepository.save(existing);
    }

    @DeleteMapping("/{id}")
    public void deleteManager(@PathVariable Long id) {
        managerRepository.deleteById(id);
    }
}

