package ifrn.pi.comercio.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ifrn.pi.comercio.models.Convidado;
import ifrn.pi.comercio.models.Evento;
import ifrn.pi.comercio.repositories.ConvidadoRepository;
import ifrn.pi.comercio.repositories.EventoRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/comercio")
public class ComercioController {
	
	@Autowired
	private EventoRepository er;
	@Autowired
	private ConvidadoRepository cr;

	@GetMapping("/form")
	public String form(Evento evento) {
		return "comercio/formEvento";
	}
	
	@PostMapping
	public String salvar(@Valid Evento evento, BindingResult result, RedirectAttributes attributes) {
		
		if(result.hasErrors()) {
			return form(evento);
		}
		
		System.out.println(evento);
		er.save(evento);
		attributes.addFlashAttribute("mensagem", "Evento salvo com sucesso!");
		
		return "redirect:/comercio";
	}
	
	@GetMapping
	public ModelAndView listar() {
		List<Evento> eventos = er.findAll();
		ModelAndView mv = new ModelAndView("comercio/lista");
		mv.addObject("eventos", eventos);
		return mv;
	}
	
	@GetMapping("/{id}")
	public ModelAndView detalhar(@PathVariable Long id, Convidado convidado) {
		ModelAndView md = new ModelAndView();
		Optional<Evento> opt = er.findById(id);
		if(opt.isEmpty()) {
			md.setViewName("redirect:/comercio");
			return md;
		}
		md.setViewName("comercio/detalhes");
		Evento evento = opt.get();
		md.addObject("evento", evento);
		
		List<Convidado> convidados = cr.findByEvento(evento);
		md.addObject("convidados", convidados);
		
		return md;
	}
	
	@PostMapping("/{idEvento}")
	public String salvarConvidado(@PathVariable Long idEvento, Convidado convidado) {
		
		System.out.println("Id do evento: " + idEvento);
		System.out.println(convidado);
		
		Optional<Evento> opt = er.findById(idEvento);
		if(opt.isEmpty()) {
			return "redirect:/comercio";
		}
		
		Evento evento = opt.get();
		convidado.setEvento(evento);
		
		cr.save(convidado);
		
		return "redirect:/comercio/{idEvento}";
	}
	
	@GetMapping("/{id}/selecionar")
	public ModelAndView selecionarEvento(@PathVariable Long id) {
		ModelAndView md = new ModelAndView(); 
		Optional<Evento> opt = er.findById(id);
		if(opt.isEmpty()) {
			md.setViewName("redirect:/comercio");
			return md;
		}
		
		Evento evento = opt.get();
		md.setViewName("comercio/formEvento");
		md.addObject("evento", evento);
		
		return md;
	}
	
	@GetMapping("/{idEvento}/convidados/{idConvidado}/selecionar")
	public ModelAndView selecionarConvidado(@PathVariable Long idEvento, @PathVariable Long idConvidado) {
		ModelAndView md = new ModelAndView();
		
		Optional<Evento> optEvento = er.findById(idEvento);
		Optional<Convidado> optConvidado = cr.findById(idConvidado);
		
		if(optEvento.isEmpty() || optConvidado.isEmpty()) {
			md.setViewName("redirect:/comercio");
			return md;
		}
		
		Evento evento = optEvento.get();
		Convidado convidado = optConvidado.get();
		
		if(evento.getId() != convidado.getEvento().getId()) {
			md.setViewName("redirect:/comercio");
			return md;
		}
		
		md.setViewName("eventos/detalhes");
		md.addObject("convidado", convidado);
		md.addObject("evento", evento);
		md.addObject("convidados", cr.findByEvento(evento));
		
		return md;
	}
	
	@GetMapping("/{id}/remover")
	public String apagarEvento(@PathVariable Long id, RedirectAttributes attributes) {
		
		Optional<Evento> opt = er.findById(id);
		
		if(!opt.isEmpty()) {
			Evento evento = opt.get();
			
			List<Convidado> convidados = cr.findByEvento(evento);
			
			cr.deleteAll(convidados);
			er.delete(evento);
			attributes.addFlashAttribute("mensagem", "Evento removido com sucesso!");
		}
		return "redirect:/comercio";
	}
	
	@GetMapping("/{idEvento}/convidados/{idConvidado}/remover")
	public String apagarConvidado(@PathVariable Long idEvento, @PathVariable Long idConvidado) {
		
		Optional<Convidado> opt = cr.findById(idConvidado);
		
		if(!opt.isEmpty()) {
			Convidado convidado = opt.get();
			cr.delete(convidado);
		}
		return "redirect:/comercio/{idEvento}";
	}

}
