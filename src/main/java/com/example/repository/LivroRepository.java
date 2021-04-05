package com.example.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.models.Evento;
import com.example.models.Livro;

public interface LivroRepository extends CrudRepository<Livro, String> {
	Iterable<Livro> findByEvento(Evento evento);
}
