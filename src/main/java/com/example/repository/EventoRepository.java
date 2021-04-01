package com.example.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.models.Evento;

public interface EventoRepository extends CrudRepository<Evento, String>{
	Evento findByCodigo(long codigo);//procura codigo que recebeu no parametro no banco 
}
