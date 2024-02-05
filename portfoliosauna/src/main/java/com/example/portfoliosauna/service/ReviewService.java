package com.example.portfoliosauna.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.portfoliosauna.entity.House;
import com.example.portfoliosauna.entity.Review;
import com.example.portfoliosauna.entity.User;
import com.example.portfoliosauna.form.ReviewEditForm;
import com.example.portfoliosauna.form.ReviewRegisterForm;
import com.example.portfoliosauna.repository.ReviewRepository;

@Service
public class ReviewService {
	private final ReviewRepository reviewRepository; 
	
	public ReviewService(ReviewRepository reviewRepository)  {
		this.reviewRepository = reviewRepository;
	}
	
	@Transactional
	public void create(House house, User user, ReviewRegisterForm reviewRegisterForm) {
		Review review = new Review();
		
		review.setHouse(house);
		review.setUser(user);
		review.setScore(reviewRegisterForm.getScore());
		review.setContent(reviewRegisterForm.getContent());
		
		reviewRepository.save(review);
	}
	
	@Transactional
	public void update(ReviewEditForm reviewEditForm) {
		Review review = reviewRepository.getReferenceById(reviewEditForm.getId());
		
		review.setScore(reviewEditForm.getScore());
		review.setContent(reviewEditForm.getContent());
		
		reviewRepository.save(review);
	}
	
	
	public boolean hasUserAlreadyReviewed(House house, User user) {
		return reviewRepository.findByHouseAndUser(house, user) != null;
	}
}


