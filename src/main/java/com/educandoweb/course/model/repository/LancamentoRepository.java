package com.educandoweb.course.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.educandoweb.course.model.entity.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

}
