package com.zerava.closetapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zerava.closetapi.model.Outfit;
import com.zerava.closetapi.model.User;
import com.zerava.closetapi.repository.OutfitRepository;

@Service
public class OutfitService {
	
	@Autowired
	OutfitRepository outfitRepository; 
	
	public void createOutfit(Outfit outfit) {
		outfitRepository.save(outfit);
	}
	
	public List<Optional<Outfit>> findOutfitByUser(User user){
		List<Optional<Outfit>> outfitlist = outfitRepository.findOutfitByUser(user);
		return outfitlist;
		
	}
	public void deletOutfit(Long id) {
		outfitRepository.deleteById(id);
	}

}
