package com.crud_tareas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crud_tareas.dto.ResponsableDto;
import com.crud_tareas.entity.Responsable;

@Repository
public interface ResponsableRepository extends JpaRepository<Responsable, Long> {

	Responsable save(ResponsableDto responsable);}
