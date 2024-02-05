package com.example.portfoliosauna.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.portfoliosauna.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	public Role findByName(String name); 
}
