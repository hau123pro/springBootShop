package com.laptrinhjava.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.laptrinhjava.demo.DTO.FilterPrice;

@Repository
public interface FilterPriceRepository extends JpaRepository<FilterPrice,Integer>{

}
