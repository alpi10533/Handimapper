package com.isep.handimapper.service;

import com.isep.handimapper.business.NoteEntity;
import com.isep.handimapper.business.PlaceEntity;
import com.isep.handimapper.business.ReviewEntity;
import com.isep.handimapper.business.UserEntity;
import com.isep.handimapper.dao.PlaceRepository;
import com.isep.handimapper.util.NoteDto;
import com.isep.handimapper.util.PlaceDto;
import com.isep.handimapper.util.ReviewDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlaceService {
    private final PlaceRepository placeRepository;

    @Autowired
    public PlaceService(PlaceRepository placeRepository){
        this.placeRepository = placeRepository;
    }

    public void savePlace(String idPlace, PlaceDto placeDto, UserEntity userEntity){
        PlaceEntity placeEntity = new PlaceEntity();
        placeEntity.setIdPlace(idPlace);
        placeEntity.setName(placeDto.getName());
        placeRepository.save(placeEntity);
    }

    public PlaceEntity findPlaceById(String id) {
        return placeRepository.findByIdPlace(id);
    }
}
