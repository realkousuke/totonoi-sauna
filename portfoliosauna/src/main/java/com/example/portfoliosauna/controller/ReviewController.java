package com.example.portfoliosauna.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.portfoliosauna.entity.House;
import com.example.portfoliosauna.entity.Review;
import com.example.portfoliosauna.entity.User;
import com.example.portfoliosauna.form.ReviewEditForm;
import com.example.portfoliosauna.form.ReviewRegisterForm;
import com.example.portfoliosauna.repository.HouseRepository;
import com.example.portfoliosauna.repository.ReviewRepository;
import com.example.portfoliosauna.security.UserDetailsImpl;
import com.example.portfoliosauna.service.ReviewService;

@Controller
@RequestMapping("/houses/{houseId}/reviews")
public class ReviewController {
	private final HouseRepository houseRepository;
	private final ReviewRepository reviewRepository;
	private final ReviewService reviewService;
	
	public ReviewController(HouseRepository houseRepository, ReviewRepository reviewRepository, ReviewService reviewService) {
		this.houseRepository = houseRepository;
		this.reviewRepository = reviewRepository;
		this.reviewService = reviewService;
	}
	
	@GetMapping
	 public String index(@PathVariable(name = "houseId") Integer houseId, @PageableDefault(page = 0, size = 10, sort = "id") Pageable pageable, Model model) {
		House house = houseRepository.getReferenceById(houseId);
		Page<Review> reviewPage = reviewRepository.findByHouseOrderByCreatedAtDesc(house, pageable);
		
		model.addAttribute("house", house);
		model.addAttribute("reviewPage", reviewPage);
		
		return "reviews/index";
	}
	
	@GetMapping("/register")
	public String register(@PathVariable(name = "houseId") Integer houseId, Model model) {
		House house = houseRepository.getReferenceById(houseId);
		
		model.addAttribute("reviewRegisterForm", new ReviewRegisterForm());
		model.addAttribute("house", house);
		return "reviews/register";
	}
	
	@PostMapping("/create")
	public String create(@PathVariable(name = "houseId") Integer houseId, 
			            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl, 
			            @ModelAttribute @Validated ReviewRegisterForm reviewRegisterForm, 
			            BindingResult bindingResult,
			            RedirectAttributes redirectAttributes,
						Model model) 
		{
		House house = houseRepository.getReferenceById(houseId);
		User user = userDetailsImpl.getUser();
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("house", house);
			return "reviews/register";
		}
				
		reviewService.create(house, user, reviewRegisterForm);
		redirectAttributes.addFlashAttribute("successMessage", "レビューを投稿しました。");
		
		return "redirect:/houses/{houseId}";
	}
	
	@GetMapping("/{reviewId}/edit")
	public String edit(@PathVariable(name = "houseId") Integer houseId, @PathVariable(name = "reviewId") Integer reviewId, Model model) {
		House house = houseRepository.getReferenceById(houseId);
		Review review = reviewRepository.getReferenceById(reviewId);
		
		ReviewEditForm reviewEditForm = new ReviewEditForm(review.getId(), review.getScore(), review.getContent());
		
		model.addAttribute("house", house);
		model.addAttribute("review", review);
		model.addAttribute("reviewEditForm", reviewEditForm);
		
		return "reviews/edit";
	}
	
	
	@PostMapping("/{reviewId}/update")
	public String update(@PathVariable(name = "houseId") Integer houseId, 
			              @PathVariable(name = "reviewId") Integer reviewId,
			              @ModelAttribute @Validated ReviewEditForm reviewEditForm,
			              BindingResult bindingResult,
			              RedirectAttributes redirectAttributes,
			              Model model)
	{
		House house = houseRepository.getReferenceById(houseId);
		Review review = reviewRepository.getReferenceById(reviewId);
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("house",house);
			model.addAttribute("review",review);
			return "reviews/edit";
		}
		
		
		reviewService.update(reviewEditForm);
		redirectAttributes.addFlashAttribute("successMessage", "レビューを編集しました");
		
		return "redirect:/houses/{houseId}";
	
	}
	
	
	@PostMapping("{revireId}/delete")
	public String delete(@PathVariable(name = "revireId") Integer reviewId, @PathVariable(name = "houseId") Integer houseId, RedirectAttributes redirectAttributes) {
		reviewRepository.deleteById(reviewId);
		
		redirectAttributes.addFlashAttribute("successMessage","レビューを削除しました");
		
		return "redirect:/houses/{houseId}";
	}
}
