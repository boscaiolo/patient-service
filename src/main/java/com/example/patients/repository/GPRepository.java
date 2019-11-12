package com.example.patients.repository;

import com.example.patients.domain.GP;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GPRepository extends CrudRepository<GP, Long>{

}
