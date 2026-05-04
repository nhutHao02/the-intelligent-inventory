package com.haotran.theintlligentinventory.repository;

import com.haotran.theintlligentinventory.entity.Dealership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DealershipRepository extends JpaRepository<Dealership, Long> {
    Optional<Dealership> findById(Long id);
}
