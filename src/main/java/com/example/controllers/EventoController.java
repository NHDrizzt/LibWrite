package com.example.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.models.Evento;
import com.example.models.Livro;
import com.example.repository.EventoRepository;
import com.example.repository.LivroRepository;

@Controller
public class EventoController {
	// injeção de dependencia(cria nova instancia automaticamente toda vez q e
	// chamado)
	@Autowired
	private EventoRepository er;
	@Autowired
	private LivroRepository lr;

	@RequestMapping(value = "/cadastrarEvento", method = RequestMethod.GET)
	public String form() {
		return "evento/formEvento";
	}

	@RequestMapping(value = "/cadastrarEvento", method = RequestMethod.POST)
	public String form(Evento evento) {
		er.save(evento);
		return "redirect:/cadastrarEvento";
	}

	@RequestMapping("/eventos")
	public ModelAndView listaEventos() {
		ModelAndView mv = new ModelAndView("index"); // modelandview para passar a view e a model em um unico returno
		Iterable<Evento> eventos = er.findAll(); // busca no banco
		mv.addObject("evento", eventos);// view e model "evento q ta na view e vai substituir pelos dados q estao no
										// banco"

		return mv;
	}

	@RequestMapping(value = "/detalhesEvento/{codigo}", method = RequestMethod.GET)
	public ModelAndView detalhesEvento(@PathVariable("codigo") long codigo) {
		Evento evento = er.findByCodigo(codigo); // invoca o metodo no repository do crud para procurar o evento com o
													// determinado codigo e guarda-lo na var evento.
		ModelAndView mv = new ModelAndView("evento/detalhesEvento");
		mv.addObject("evento", evento);

		Iterable<Livro> livros = lr.findByEvento(evento);
		mv.addObject("livros", livros);
		return mv;
	}

	@RequestMapping(value = "/detalhesEvento/{codigo}", method = RequestMethod.POST)
	public String detalhesEvento(@PathVariable("codigo") long codigo, @Valid Livro livro, BindingResult result,
			RedirectAttributes attributes) {
		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos!");
			return "redirect:/detalhesEvento/{codigo}";
		}
		Evento evento = er.findByCodigo(codigo);
		livro.setEvento(evento);
		lr.save(livro);
		attributes.addFlashAttribute("mensagem", "Cadastrado!");

		return "redirect:/detalhesEvento/{codigo}";
	}

	@RequestMapping("/deletar")
	public String deletarEvento(long codigo) {
		Evento evento = er.findByCodigo(codigo);
		er.delete(evento);
		return "redirect:/eventos";
	}

	@RequestMapping("/deletarLivro")
	public String deletarLivro(int id) {
		Livro livro = lr.findById(id);
		lr.delete(livro);
		
		Evento evento = livro.getEvento();
		long codigoLong = evento.getCodigo();
		String codigo = "detalhesEvento/" + codigoLong;
		return "redirect:/" + codigo;

	}
}
