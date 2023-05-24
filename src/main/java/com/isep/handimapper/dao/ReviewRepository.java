package com.isep.handimapper.dao;

import com.isep.handimapper.business.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<ReviewEntity,Long> {

}
