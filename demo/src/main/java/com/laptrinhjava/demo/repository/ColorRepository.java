package com.laptrinhjava.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.laptrinhjava.demo.DTO.Color;

public interface ColorRepository extends JpaRepository<Color, Integer>{

}
