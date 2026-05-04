package com.haotran.theintlligentinventory.repository;

import com.haotran.theintlligentinventory.entity.VehicleAction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleActionRepository extends JpaRepository<VehicleAction, Long> {
    @EntityGraph(attributePaths = {"actionType", "createBy"})
    Page<VehicleAction> findAllByCreateBy_IdAndVehicle_Id(Long userId, Long vehicleId, Pageable pageable);
}
