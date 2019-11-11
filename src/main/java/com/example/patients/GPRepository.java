package com.example.patients;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GPRepository extends CrudRepository<GP, Long>{

}
