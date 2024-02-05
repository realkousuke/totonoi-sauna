package com.example.portfoliosauna.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "houses")
@Data
public class House {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "image_name")
    private String imageName;
    
    @Column(name = "address")
    private String address;
    
    @Column(name = "access")
    private String access;
    
    @Column(name = "phone_number")
    private String phoneNumber;
    
    @Column(name = "holiday")
    private String holiday;
    
    @Column(name = "opening_hours")
    private String opening_hours;
    
    @Column(name = "price")
    private Integer price;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "created_at", insertable = false, updatable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private Timestamp updatedAt;

}
