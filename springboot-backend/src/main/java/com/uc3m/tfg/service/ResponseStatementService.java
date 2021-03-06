
package com.uc3m.tfg.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.uc3m.tfg.model.ResponseStatement;

public interface ResponseStatementService {
	
	public Iterable<ResponseStatement> findAll();
	
	public Page<ResponseStatement> findAll(Pageable pageable);
	
	public Optional<ResponseStatement> findById(Long id);
	
	public ResponseStatement save(ResponseStatement responseStatement);
	
	public void deleteById(Long id);		

}
