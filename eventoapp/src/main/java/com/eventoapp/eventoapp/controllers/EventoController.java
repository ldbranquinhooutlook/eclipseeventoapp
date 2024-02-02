package com.eventoapp.eventoapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.eventoapp.eventoapp.models.Convidado;
import com.eventoapp.eventoapp.models.Evento;
import com.eventoapp.eventoapp.repository.ConvidadoRepository;
import com.eventoapp.eventoapp.repository.EventoRepository;

import jakarta.validation.Valid;

@Controller
public class EventoController {

	@Autowired
	private EventoRepository er;
	
	@Autowired
	private ConvidadoRepository cr;

	@RequestMapping(value="/cadastrarEvento",method=RequestMethod.GET)
	public String form() {
		return "evento/formEvento.html";
	}
	
	@RequestMapping(value="/cadastrarEvento",method=RequestMethod.POST)
	public String form(Evento evento) {
		
		er.save(evento);
		return "redirect:/cadastrarEvento";
	}
	
	@RequestMapping("/eventos")
	public ModelAndView listaEventos(){
	
		ModelAndView mv = new ModelAndView("index.html");
		Iterable<Evento> eventos = er.findAll();
		mv.addObject("eventos",eventos);
	
		return mv;
	}	
	
	@RequestMapping("/deletarEvento")
	public String deletarEvento(long codigo) {
		Evento evento = er.findByCodigo(codigo);
		er.delete(evento);
		return "redirect:/eventos";
	}	
	
	@RequestMapping(value="/{codigo}", method=RequestMethod.GET)
	public ModelAndView detalhesEvento(@PathVariable("codigo") long codigo) {
		Evento evento = er.findByCodigo(codigo);
		ModelAndView mv = new ModelAndView("evento/detalhesEvento");
		mv.addObject("evento",evento);

		Iterable<Convidado> convidados = cr.findByEvento(evento);
		mv.addObject("convidados",convidados);

		return mv;
	}
	
	@RequestMapping(value="/{codigo}", method=RequestMethod.POST)
	public String detalhesEventoPost(@PathVariable("codigo") long codigo, @Valid Convidado convidado, BindingResult result, RedirectAttributes attributes ) {
		
		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os Campos!");
			return "redirect:/{codigo}";
		}else {
		Evento evento = er.findByCodigo(codigo);
		convidado.setEvento(evento);
		cr.save(convidado);
		attributes.addFlashAttribute("mensagem", "Sucesso!");
		return "redirect:/{codigo}";
		}
		
	}	
	
	@RequestMapping("/deletarConvidado")
	public String deletarConvidado(String rg) {
		
		Convidado convidado = cr.findByRg(rg);
		cr.delete(convidado);
	
		Evento evento = convidado.getEvento();
		long codigoLong = evento.getCodigo();
		String codigo = ""+codigoLong;
		return "redirect:/"+codigo;
		
	}

	
}






















