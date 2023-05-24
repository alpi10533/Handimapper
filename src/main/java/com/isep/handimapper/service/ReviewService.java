package com.isep.handimapper.service;

import com.isep.handimapper.business.ReviewEntity;
import com.isep.handimapper.business.UserEntity;
import com.isep.handimapper.dao.ReviewRepository;
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

    public void saveReview(ReviewDto reviewDto, UserEntity userEntity){
        ReviewEntity reviewEntity = new ReviewEntity();
        reviewEntity.setComment(reviewDto.getReview());
        reviewEntity.setUserEntity(userEntity);
        reviewRepository.save(reviewEntity);

    }
}
