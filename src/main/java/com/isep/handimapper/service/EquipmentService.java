package com.isep.handimapper.service;

import com.isep.handimapper.business.EquipmentEntity;
import com.isep.handimapper.business.PlaceEntity;
import com.isep.handimapper.business.UserEntity;
import com.isep.handimapper.dao.EquipmentRepository;
import com.isep.handimapper.util.EquipmentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentService {
    private final EquipmentRepository equipmentRepository;
    @Autowired
    public EquipmentService(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    public void savEquipment(EquipmentDto equipmentDto, UserEntity userEntity, PlaceEntity placeEntity){
        EquipmentEntity equipmentEntity = new EquipmentEntity();
        equipmentEntity.setType(equipmentDto.getType());
        equipmentEntity.setStatus(equipmentDto.getStatus());
        equipmentEntity.setUserEntity(userEntity);
        equipmentEntity.setPlaceEntity(placeEntity);
        equipmentRepository.save(equipmentEntity);
    }

    public List<EquipmentEntity> findAllEquipmentsByPlace(PlaceEntity placeEntity) {
        return equipmentRepository.findAllByPlace(placeEntity);
    }

}
