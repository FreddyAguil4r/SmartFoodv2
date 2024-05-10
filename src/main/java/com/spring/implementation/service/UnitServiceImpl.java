package com.spring.implementation.service;

import com.spring.implementation.domain.model.Unit;
import com.spring.implementation.domain.repository.UnitRepository;
import com.spring.implementation.domain.service.UnitService;
import com.spring.implementation.dto.save.SaveUnitDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UnitServiceImpl implements UnitService {

    static final String UNIT_NOT_FOUND = "Unit not found with ID: ";
    private UnitRepository unitRepository;

    @Autowired
    public UnitServiceImpl(UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
    }

    public UnitServiceImpl() {
    }

    @Override
    public Unit createUnit(SaveUnitDto unit) {
        Unit newUnit = new Unit();
        newUnit.setName(unit.getName());
        newUnit.setAbbreviation(unit.getAbbreviation());
        return unitRepository.save(newUnit);
    }

    @Override
    public Unit getUnitById(Integer unitId) {
        return unitRepository.findById(unitId)
                .orElseThrow(() -> new NoSuchElementException(UNIT_NOT_FOUND + unitId));
    }

    @Override
    public Unit updateUnit(Integer unitId, Unit unitRequest) {
        return unitRepository.findById(unitId).map
                (unit -> {
                    unit.setName(unitRequest.getName());
                    return unitRepository.save(unit);
                }).orElseThrow(() -> new NoSuchElementException(UNIT_NOT_FOUND + unitId));
    }

    @Override
    public ResponseEntity<Void> deleteUnit(Integer unitId) {
        Unit unit = unitRepository.findById(unitId)
                .orElseThrow(() -> new NoSuchElementException(UNIT_NOT_FOUND + unitId));
        unitRepository.delete(unit);
        return ResponseEntity.ok().build();
    }

    @Override
    public Iterable<Unit> getAllUnits() {
        return unitRepository.findAll();
    }
}
