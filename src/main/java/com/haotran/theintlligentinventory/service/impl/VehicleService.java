package com.haotran.theintlligentinventory.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.haotran.theintlligentinventory.dto.vehicle.VehicleFilterReq;
import com.haotran.theintlligentinventory.dto.vehicle.VehicleRes;
import com.haotran.theintlligentinventory.entity.Vehicle;
import com.haotran.theintlligentinventory.entity.enumType.VehicleStatus;
import com.haotran.theintlligentinventory.exception.AppException;
import com.haotran.theintlligentinventory.mapper.VehicleMapper;
import com.haotran.theintlligentinventory.repository.*;
import com.haotran.theintlligentinventory.service.IVehicleService;
import com.haotran.theintlligentinventory.utils.ErrorCode;
import com.haotran.theintlligentinventory.utils.response.PageResponse;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Slf4j
public class VehicleService implements IVehicleService {
    final VehicleRepository vehicleRepository;

    final DealershipRepository dealershipRepository;

    final VehicleMapper mapper;

    final RedisTemplate<String, Object> redisTemplate;

    final ObjectMapper objectMapper;

    public VehicleService(VehicleRepository vehicleRepository, DealershipRepository dealershipRepository,
                          VehicleMapper mapper, RedisTemplate<String, Object> redisTemplate, ObjectMapper objectMapper
    ) {
        this.vehicleRepository = vehicleRepository;
        this.dealershipRepository = dealershipRepository;
        this.mapper = mapper;
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public PageResponse<VehicleRes> getVehicles(VehicleFilterReq filterReq, Pageable pageable) {
        String cacheKey = String.format("vehicles:dealership:%d:page:%d:size:%d:%s",
                filterReq.getDealershipId(),
                pageable.getPageNumber(),
                pageable.getPageSize(),
                generateFilterSuffix(filterReq));

        try {
            Object cachedObject = redisTemplate.opsForValue().get(cacheKey);
            if (cachedObject != null) {
                PageResponse<VehicleRes> cachedData =
                        objectMapper.convertValue(
                                cachedObject,
                                new TypeReference<PageResponse<VehicleRes>>() {}
                        );
                return cachedData;
            }
        } catch (Exception e) {
            log.error("event=Redis error, falling back to database", e);
        }

        if (!dealershipRepository.existsById(filterReq.getDealershipId())){
            log.warn("event=dealership_not_found dealershipId={}", filterReq.getDealershipId());
            throw new AppException(ErrorCode.DEALERSHIP_NOT_FOUND);
        }

        Specification<Vehicle> spec = Specification
                .where(hasDealership(filterReq.getDealershipId()))
                .and(hasColor(filterReq.getColor()))
                .and(hasMake(filterReq.getMake()))
                .and(hasModel(filterReq.getModel()))
                .and(hasYear(filterReq.getYear()))
                .and(hasStatus(filterReq.getStatus()))
                ;
        Page<VehicleRes> pageRes = vehicleRepository.findAll(spec, pageable).map(mapper::toVehicleRes);

        PageResponse<VehicleRes> response = PageResponse.<VehicleRes>builder()
                .content(pageRes.getContent())
                .page(pageRes.getNumber())
                .size(pageRes.getSize())
                .totalElements(pageRes.getTotalElements())
                .build();

        try {
            redisTemplate.opsForValue().set(cacheKey, response, Duration.ofHours(1));
            log.info("event=Cache miss - Data saved to Redis with key={}", cacheKey);
        } catch (Exception e) {
            log.error("event=Failed to save data to Redis error", e);
        }

        return response;
    }

    private String generateFilterSuffix(VehicleFilterReq filterReq) {
        return String.format("color:%s:make:%s:model:%s:year:%s",
                filterReq.getColor(), filterReq.getMake(), filterReq.getModel(), filterReq.getYear());
    }

    private Specification<Vehicle> hasDealership(Long dealershipId) {
        return (root, query, cb) ->
                cb.equal(root.get("dealership").get("id"), dealershipId);
    }

    private Specification<Vehicle> hasColor(String color) {
        return (root, query, cb) ->
                color == null ? null : cb.equal(cb.lower(root.get("color")), color.toLowerCase());
    }

    private Specification<Vehicle> hasMake(String make) {
        return (root, query, cb) ->
                make == null ? null : cb.equal(cb.lower(root.get("make")), make.toLowerCase());
    }

    private Specification<Vehicle> hasModel(String model) {
        return (root, query, cb) ->
                model == null ? null : cb.equal(cb.lower(root.get("model")), model.toLowerCase());
    }

    private Specification<Vehicle> hasYear(Integer year) {
        return (root, query, cb) ->
                year == null ? null : cb.equal(root.get("year"), year);
    }

    private Specification<Vehicle> hasStatus(VehicleStatus status) {
        return (root, query, cb) ->
                status == null ? null : cb.equal(root.get("status"), status);
    }
}
