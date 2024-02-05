package com.example.portfoliosauna.service;
 
 import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.portfoliosauna.entity.House;
import com.example.portfoliosauna.form.HouseEditForm;
import com.example.portfoliosauna.form.HouseRegisterForm;
import com.example.portfoliosauna.repository.HouseRepository;
 
 @Service
 public class HouseService {
     private final HouseRepository houseRepository;    
     
     public HouseService(HouseRepository houseRepository) {
         this.houseRepository = houseRepository;        
     }    
     
     @Transactional
     public void create(HouseRegisterForm houseRegisterForm) {
         House house = new House();        
         MultipartFile imageFile = houseRegisterForm.getImageFile();
         
         if (!imageFile.isEmpty()) {
             String imageName = imageFile.getOriginalFilename(); 
             String hashedImageName = generateNewFileName(imageName);
             Path filePath = Paths.get("src/main/resources/static/storage/" + hashedImageName);
             copyImageFile(imageFile, filePath);
             house.setImageName(hashedImageName);
         }
         
         house.setName(houseRegisterForm.getName());  
         house.setAddress(houseRegisterForm.getAddress());
         house.setAccess(houseRegisterForm.getAccess());
         house.setPhoneNumber(houseRegisterForm.getPhoneNumber());
         house.setHoliday(houseRegisterForm.getHoliday());
         house.setOpening_hours(houseRegisterForm.getOpening_hours());
         house.setPrice(houseRegisterForm.getPrice());
         house.setDescription(houseRegisterForm.getDescription());
                              
         houseRepository.save(house);
     }  
     
     @Transactional
     public void update(HouseEditForm houseEditForm) {
         House house = houseRepository.getReferenceById(houseEditForm.getId());
         MultipartFile imageFile = houseEditForm.getImageFile();
         
         if (!imageFile.isEmpty()) {
             String imageName = imageFile.getOriginalFilename(); 
             String hashedImageName = generateNewFileName(imageName);
             Path filePath = Paths.get("src/main/resources/static/storage/" + hashedImageName);
             copyImageFile(imageFile, filePath);
             house.setImageName(hashedImageName);
         }
         
         house.setName(houseEditForm.getName());  
         house.setAddress(houseEditForm.getAddress());
         house.setAccess(houseEditForm.getAccess());
         house.setPhoneNumber(houseEditForm.getPhoneNumber());
         house.setHoliday(houseEditForm.getHoliday());
         house.setOpening_hours(houseEditForm.getOpening_hours());
         house.setPrice(houseEditForm.getPrice());
         house.setDescription(houseEditForm.getDescription());
                     
         houseRepository.save(house);
     }    
     
     // UUIDを使って生成したファイル名を返す
     public String generateNewFileName(String fileName) {
         String[] fileNames = fileName.split("\\.");                
         for (int i = 0; i < fileNames.length - 1; i++) {
             fileNames[i] = UUID.randomUUID().toString();            
         }
         String hashedFileName = String.join(".", fileNames);
         return hashedFileName;
     }     
     
     // 画像ファイルを指定したファイルにコピーする
     public void copyImageFile(MultipartFile imageFile, Path filePath) {           
         try {
             Files.copy(imageFile.getInputStream(), filePath);
         } catch (IOException e) {
             e.printStackTrace();
         }          
     } 
 }