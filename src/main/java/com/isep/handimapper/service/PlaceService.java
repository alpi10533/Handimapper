package com.isep.handimapper.service;

import com.isep.handimapper.business.PlaceEntity;
import com.isep.handimapper.dao.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlaceService {
    private final PlaceRepository placeRepository;

    @Autowired
    public PlaceService(PlaceRepository placeRepository){
        this.placeRepository = placeRepository;
    }

    public PlaceEntity savePlace(String idPlace){
        PlaceEntity placeEntity = new PlaceEntity(idPlace);
        placeRepository.save(placeEntity);
        return placeEntity;
    }

    public PlaceEntity findPlaceById(String id) {
        return placeRepository.findByIdPlace(id);
    }

}
