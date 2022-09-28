package com.vinaudit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vinaudit.model.vinmaster;

public interface vinauditrepo extends JpaRepository<vinmaster,String> {
	

}
