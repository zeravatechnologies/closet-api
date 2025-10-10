package com.zerava.closetapi.model;

import java.time.LocalDateTime;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "outfits")
public class Outfit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
	private String title; 
	private String description; 
	private String Season; 
	private LocalDateTime dateCreated=LocalDateTime.now(); 
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user ; 
	
	@ManyToOne(fetch= FetchType.LAZY)
	@JoinColumn(name = "category_id")
	private Category category;

}
