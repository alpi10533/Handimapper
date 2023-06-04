package com.isep.handimapper.dao;

import com.isep.handimapper.business.EquipmentEntity;
import com.isep.handimapper.business.PlaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EquipmentRepository extends JpaRepository<EquipmentEntity,Long> {

    @Query("select u from EquipmentEntity u where u.placeEntity = :x")
    List<EquipmentEntity> findAllByPlace(@Param("x") PlaceEntity placeEntity);

}
