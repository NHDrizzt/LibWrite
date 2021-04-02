package com.example.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Livro {
	@Id
	private int id;
	private String nomeLivro;
	private String genero;
	
	@ManyToOne
	private Evento evento;
	
	public Evento getEvento() {
		return evento;
	}
	public void setEvento(Evento evento) {
		this.evento = evento;
	}
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNomelivro() {
		return nomeLivro;
	}
	public void setNomelivro(String nomeLivro) {
		this.nomeLivro = nomeLivro;
	}
}
