package com.isep.handimapper.dao;

import com.isep.handimapper.business.PlaceEntity;
import com.isep.handimapper.business.ReviewEntity;
import com.isep.handimapper.business.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<ReviewEntity,Long> {

    @Query("select u from ReviewEntity u where u.userEntity = :x and u.placeEntity = :y")
    ReviewEntity findByUserAndPlace(@Param("x") UserEntity userEntity, @Param("y") PlaceEntity placeEntity);

    @Query("select u from ReviewEntity u where u.placeEntity = :x")
    List<ReviewEntity> findAllByPlace(@Param("x") PlaceEntity placeEntity);

}
