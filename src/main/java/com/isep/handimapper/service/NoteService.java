package com.isep.handimapper.service;

import com.isep.handimapper.business.NoteEntity;
import com.isep.handimapper.business.PlaceEntity;
import com.isep.handimapper.business.UserEntity;
import com.isep.handimapper.dao.NoteRepository;
import com.isep.handimapper.util.NoteDto;
import com.isep.handimapper.util.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class NoteService {
    private final NoteRepository noteRepository;
    @Autowired
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public void saveNote(NoteDto noteDto, UserEntity userEntity, PlaceEntity placeEntity){
        NoteEntity noteEntity = new NoteEntity();
        noteEntity.setNote(Integer.parseInt(noteDto.getNote()));
        noteEntity.setUserEntity(userEntity);
        noteEntity.setPlaceEntity(placeEntity);
        noteRepository.save(noteEntity);
    }

    public void updateNote(NoteEntity noteEntity, NoteDto noteDto){
        noteEntity.setNote(Integer.parseInt(noteDto.getNote()));
        noteRepository.save(noteEntity);
    }

    public String calculateStarMean(Set<NoteEntity> noteEntities) {
        double average = noteEntities.stream()
                .mapToDouble(NoteEntity::getNote)
                .average()
                .orElse(0.0);
        StringBuilder stars = new StringBuilder();
        int fullStars = (int) average;
        stars.append("★".repeat(Math.max(0, fullStars)));
        int emptyStars = 5 - stars.length();
        stars.append("☆".repeat(Math.max(0, emptyStars)));
        return stars.toString();
    }

    public NoteEntity findNoteByUserAndPlace(UserEntity userEntity, PlaceEntity placeEntity) {
        return noteRepository.findByUserAndPlace(userEntity, placeEntity);
    }
}
