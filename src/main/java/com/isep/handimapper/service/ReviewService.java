package com.isep.handimapper.service;

import com.isep.handimapper.business.NoteEntity;
import com.isep.handimapper.business.PlaceEntity;
import com.isep.handimapper.business.ReviewEntity;
import com.isep.handimapper.business.UserEntity;
import com.isep.handimapper.dao.ReviewRepository;
import com.isep.handimapper.util.NoteDto;
import com.isep.handimapper.util.ReviewDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository){
        this.reviewRepository= reviewRepository;
    }

    public void saveReview(ReviewDto reviewDto, UserEntity userEntity, PlaceEntity placeEntity){
        ReviewEntity reviewEntity = new ReviewEntity();
        reviewEntity.setReview(reviewDto.getReview());
        reviewEntity.setUserEntity(userEntity);
        reviewEntity.setPlaceEntity(placeEntity);
        reviewRepository.save(reviewEntity);
    }

    public void updateReview(ReviewEntity reviewEntity, ReviewDto reviewDto){
        reviewEntity.setReview(reviewDto.getReview());
        reviewRepository.save(reviewEntity);
    }

    public ReviewEntity findReviewByUserAndPlace(UserEntity userEntity, PlaceEntity placeEntity) {
        return reviewRepository.findByUserAndPlace(userEntity, placeEntity);
    }

}
