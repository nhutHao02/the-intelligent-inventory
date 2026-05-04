package com.haotran.theintlligentinventory.repository;

import com.haotran.theintlligentinventory.entity.ActionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionTypeRepository extends JpaRepository<ActionType, Long> {
}
