package com.crud_tareas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crud_tareas.entity.Puesto;

@Repository
public interface PuestoRepository extends JpaRepository<Puesto, Long> { }
