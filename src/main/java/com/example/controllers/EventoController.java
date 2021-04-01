package com.example.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.models.Evento;
import com.example.repository.EventoRepository;

@Controller
public class EventoController {
	//injeção de dependencia(cria nova instancia automaticamente toda vez q e chamado)
	@Autowired 
	private EventoRepository er;
	
	@RequestMapping(value="/cadastrarEvento", method=RequestMethod.GET)
	public String form() {
		return "evento/formEvento";
	}
	
	@RequestMapping(value="/cadastrarEvento", method=RequestMethod.POST)
	public String form(Evento evento) {
		er.save(evento);
		return "redirect:/cadastrarEvento";
	}
	
	@RequestMapping("/eventos")
	public ModelAndView listaEventos() {
		ModelAndView mv = new ModelAndView("index"); //modelandview para passar a view e a model em um unico returno
		Iterable<Evento> eventos = er.findAll(); //busca no banco
		mv.addObject("evento", eventos);// view e model "evento q ta na view e vai substituir pelos dados q estao no banco"

		return mv;
	}
	
	@RequestMapping("/{codigo}")
	public ModelAndView detalhesEvento(@PathVariable("codigo") long codigo) {
		Evento evento =er.findByCodigo(codigo); //invoca o metodo no repository do crud para procurar o evento com o determinado codigo e guarda-lo na var evento.
		ModelAndView mv = new ModelAndView("detalhesEvento");
		mv.addObject("evento", evento);
		return mv;
	}
}
