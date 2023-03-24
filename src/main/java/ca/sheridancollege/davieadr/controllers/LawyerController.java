package ca.sheridancollege.davieadr.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import ca.sheridancollege.davieadr.beans.Art;
import ca.sheridancollege.davieadr.beans.Lawyer;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class LawyerController {
	
	final private String REST_URL_LAWYERS = "http://localhost:8086/lawyers/";
	final private String REST_URL_ART = "http://localhost:8086/art/";
	
	@GetMapping("/")
	public String loadroot() {
		return "root.html";
	}
	
	@GetMapping("/viewLawyers")
	public String loadViewLawyers(Model model, RestTemplate restTemplate) {
		ResponseEntity<Lawyer[]> responseEntity = 
			restTemplate.getForEntity(REST_URL_LAWYERS, Lawyer[].class);
		model.addAttribute("lawyerList", responseEntity.getBody());
		return "viewLawyers.html";
	}
	
	@GetMapping("/viewArt")
	public String loadViewArt(Model model, RestTemplate restTemplate) {
		ResponseEntity<Art[]> responseEntity = 
			restTemplate.getForEntity(REST_URL_ART, Art[].class);
		model.addAttribute("artList", responseEntity.getBody());
		return "viewArt.html";
	}
	
	@GetMapping("/addLawyer")
	public String loadAddLawyer(Model model) {
		model.addAttribute("lawyer", new Lawyer());
		return "addLawyer.html";
	}
	
	@PostMapping("/addLawyer")
	public String processAddLawyer(@ModelAttribute Lawyer lawyer, 
			RestTemplate restTemplate) {
		restTemplate.postForLocation(REST_URL_LAWYERS, lawyer);
		return "redirect:/addLawyer";
	}
	
	@GetMapping("/addArt")
	public String loadAddArt(Model model, RestTemplate restTemplate) {
		model.addAttribute("art", new Art());
		ResponseEntity<Lawyer[]> responseEntity = 
				restTemplate.getForEntity(REST_URL_LAWYERS, Lawyer[].class);
		model.addAttribute("lawyerList", responseEntity.getBody());
		return "addArt.html";
	}
	
	@PostMapping("/addArt")
	public String processAddArt(@ModelAttribute Art art, RestTemplate restTemplate, 
			@RequestParam String lawyerId) {
		ResponseEntity<Lawyer> responseEntity = 
			restTemplate.getForEntity(REST_URL_LAWYERS + lawyerId, Lawyer.class);
		art.setEmployeeName(responseEntity.getBody().getName());
		restTemplate.postForLocation(REST_URL_ART, art);
		return "redirect:/addArt";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteArt(@PathVariable Long id, RestTemplate restTemplate) {
		restTemplate.delete(REST_URL_ART + id);
		return "redirect:/viewArt";
	}
	
	@GetMapping("/editArt/{id}")
	public String loadEdit(@PathVariable Long id, Model model, 
			RestTemplate restTemplate) {
		ResponseEntity<Art> responseEntityArt = 
				restTemplate.getForEntity(REST_URL_ART + id, Art.class);
		model.addAttribute("art", responseEntityArt.getBody());
		ResponseEntity<Lawyer[]> responseEntityLawyers = 
				restTemplate.getForEntity(REST_URL_LAWYERS, Lawyer[].class);
		model.addAttribute("lawyerList", responseEntityLawyers.getBody());
		return "editArt.html";
	}
	
	@PostMapping("/editArt")
	public String processEdit(@ModelAttribute Art art, 
			RestTemplate restTemplate, @RequestParam String lawyerId) {
		ResponseEntity<Lawyer> responseEntity = 
				restTemplate.getForEntity(REST_URL_LAWYERS + lawyerId, Lawyer.class);
		art.setEmployeeName(responseEntity.getBody().getName());
		restTemplate.put(REST_URL_ART + art.getId(), art);
		return "redirect:/viewArt";
	}

}
