package com.isep.handimapper.dao;

import com.isep.handimapper.business.NoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<NoteEntity,Long> {

}
