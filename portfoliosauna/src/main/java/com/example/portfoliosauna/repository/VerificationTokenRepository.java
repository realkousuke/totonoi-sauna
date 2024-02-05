package com.example.portfoliosauna.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.portfoliosauna.entity.VerificationToken;
public interface VerificationTokenRepository extends JpaRepository< VerificationToken, Integer> {
    public VerificationToken findByToken(String token);
}
