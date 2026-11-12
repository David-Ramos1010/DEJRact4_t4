package com.davidramos1010.crudrapido.repository;

import org.springframework.stereotype.Repository;

import com.davidramos1010.crudrapido.entity.Student;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository 
public interface StudentRepository extends JpaRepository<Student, Long> {

}
