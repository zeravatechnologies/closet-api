package com.zerava.closetapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zerava.closetapi.model.Category;
import com.zerava.closetapi.repository.CategoryRepository;

@Service
public class CategoryService {
	@Autowired
	CategoryRepository categoryRepository;
	
	public void createCategory(Category cat) {
		categoryRepository.save(cat);
	}
	
	public List<Category> findAllCategory(){
		List<Category> categoryList =categoryRepository.findAll();
		return categoryList;
	}
	
	public void updateCategory(Category cat) {
		categoryRepository.save(cat);
	}
	
	public void deleteCategory(Long id) {
		categoryRepository.deleteById(id);
	}
	

}
