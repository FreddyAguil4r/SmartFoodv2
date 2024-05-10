package com.spring.implementation.controller;

import com.spring.implementation.domain.model.Unit;
import com.spring.implementation.domain.service.UnitService;
import com.spring.implementation.dto.save.SaveUnitDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/unit")
@CrossOrigin(origins = "*")
public class UnitController {

    private UnitService unitService;

    public UnitController(UnitService unitService) {
        this.unitService = unitService;
    }

    @PostMapping
    public Unit createUnit(@RequestBody SaveUnitDto request) {
        return unitService.createUnit(request);
    }

    @GetMapping("/{unitId}")
    public Unit getUnitById(@PathVariable Integer unitId) {
        return unitService.getUnitById(unitId);
    }

    @PutMapping("/{unitId}")
    public Unit updateUnit(@PathVariable Integer unitId, @RequestBody Unit unitRequest) {
        return unitService.updateUnit(unitId, unitRequest);
    }

    @DeleteMapping("/{unitId}")
    public void deleteUnit(@PathVariable Integer unitId) {
        unitService.deleteUnit(unitId);
    }

    @GetMapping("/all")
    public Iterable<Unit> getAllUnits() {
        return unitService.getAllUnits();
    }
}
