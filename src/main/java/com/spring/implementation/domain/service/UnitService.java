package com.spring.implementation.domain.service;

import com.spring.implementation.domain.model.Unit;
import com.spring.implementation.dto.save.SaveUnitDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface UnitService {
    //create
    Unit createUnit(SaveUnitDto unit);
    //read
    Unit getUnitById(Integer unitId);
    //update
    Unit updateUnit(Integer unitId, Unit unitRequest);
    //delete
    ResponseEntity<Void> deleteUnit(Integer unitId);
    //get all
    Iterable<Unit> getAllUnits();
}
