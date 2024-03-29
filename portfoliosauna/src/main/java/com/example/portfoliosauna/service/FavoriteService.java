package com.example.portfoliosauna.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.portfoliosauna.entity.Favorite;
import com.example.portfoliosauna.entity.House;
import com.example.portfoliosauna.entity.User;
import com.example.portfoliosauna.repository.FavoriteRepository;

@Service
public class FavoriteService {
	private final FavoriteRepository favoriteRepository;        
    
    public FavoriteService(FavoriteRepository favoriteRepository) {        
        this.favoriteRepository = favoriteRepository;        
    }     
    
    @Transactional
    public void create(House house, User user) {
        Favorite favorite = new Favorite();        
        
        favorite.setHouse(house);                
        favorite.setUser(user);
                    
        favoriteRepository.save(favorite);
    }
    
    public boolean favoriteUser(House house, User user) {
        return favoriteRepository.findByHouseAndUser(house, user) != null;
    }
}
