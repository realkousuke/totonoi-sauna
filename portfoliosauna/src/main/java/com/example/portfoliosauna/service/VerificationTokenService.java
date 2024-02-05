package com.example.portfoliosauna.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.portfoliosauna.entity.User;
import com.example.portfoliosauna.entity.VerificationToken;
import com.example.portfoliosauna.repository.VerificationTokenRepository;

@Service
public class VerificationTokenService {
 private final VerificationTokenRepository verificationTokenRepository;
     
     
     public VerificationTokenService(VerificationTokenRepository verificationTokenRepository) {        
         this.verificationTokenRepository = verificationTokenRepository;
     } 
     
     @Transactional
     public void create(User user, String token) {
         VerificationToken verificationToken = new VerificationToken();
         
         verificationToken.setUser(user);
         verificationToken.setToken(token);        
         
         verificationTokenRepository.save(verificationToken);
     }    
     
     // トークンの文字列で検索した結果を返す
     public VerificationToken getVerificationToken(String token) {
         return verificationTokenRepository.findByToken(token);
     }    
}