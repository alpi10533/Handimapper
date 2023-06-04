package com.isep.handimapper.dao;

import com.isep.handimapper.business.PlaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PlaceRepository extends JpaRepository<PlaceEntity,Long> {

    @Query("select u from PlaceEntity u where u.idPlace = :x")
    PlaceEntity findByIdPlace(@Param("x")String id);

}
