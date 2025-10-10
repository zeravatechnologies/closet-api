package com.zerava.closetapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zerava.closetapi.model.Outfit;
import com.zerava.closetapi.model.User;

@Repository
public interface OutfitRepository extends JpaRepository<Outfit, Long> {
	
	List<Optional<Outfit>> findOutfitByUser(User user);

}
