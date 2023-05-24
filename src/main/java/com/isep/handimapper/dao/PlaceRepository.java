package com.isep.handimapper.dao;

import com.isep.handimapper.business.PlaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<PlaceEntity,Long> {

}
