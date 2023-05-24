package com.isep.handimapper.dao;

import com.isep.handimapper.business.NoteEntity;
import com.isep.handimapper.business.PlaceEntity;
import com.isep.handimapper.business.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NoteRepository extends JpaRepository<NoteEntity,Long> {

    @Query("select u from NoteEntity u where u.userEntity = :x and u.placeEntity = :y")
    NoteEntity findByUserAndPlace(@Param("x") UserEntity userEntity, @Param("y") PlaceEntity placeEntity);
}
