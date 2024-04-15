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

import ca.sheridancollege.davieadr.beans.Album;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class AlbumController {
	
	final private String REST_URL_ALBUM = "https://mydiscogsrestservice.onrender.com/albums/";
	
	@GetMapping("/")
	public String loadroot() {
		return "root.html";
	}
	
	@GetMapping("/viewAlbums")
	public String loadViewAlbums(Model model, RestTemplate restTemplate) {
		ResponseEntity<Album[]> responseEntity = 
			restTemplate.getForEntity(REST_URL_ALBUM, Album[].class);
		model.addAttribute("albumList", responseEntity.getBody());
		return "viewAlbums.html";
	}
	
	@GetMapping("/viewOwned")
	public String loadOwnedAlbums(Model model, RestTemplate restTemplate) {
		ResponseEntity<Album[]> responseEntity = 
			restTemplate.getForEntity(REST_URL_ALBUM + "/viewOwned", Album[].class);
		model.addAttribute("owned", responseEntity.getBody());
		return "viewOwned.html";
	}
	
	@GetMapping("/viewWishlist")
	public String loadWishlistAlbums(Model model, RestTemplate restTemplate) {
		ResponseEntity<Album[]> responseEntity = 
			restTemplate.getForEntity(REST_URL_ALBUM + "/viewWishlist", Album[].class);
		model.addAttribute("wishlist", responseEntity.getBody());
		return "viewWishlist.html";
	}
	
	@GetMapping("/addAlbum")
	public String loadAddAlbum(Model model) {
		model.addAttribute("album", new Album());
		return "addAlbum.html";
	}
	
	@PostMapping("/addAlbum")
	public String processAddAlbum(@ModelAttribute Album album, 
			RestTemplate restTemplate) {
		restTemplate.postForLocation(REST_URL_ALBUM, album);
		return "redirect:/addAlbum";
	}
	
	// Use these methods (add to collection or wishlist?)
	
//	@GetMapping("/addArt")
//	public String loadAddArt(Model model, RestTemplate restTemplate) {
//		model.addAttribute("art", new Record());
//		ResponseEntity<Lawyer[]> responseEntity = 
//				restTemplate.getForEntity(REST_URL_LAWYERS, Lawyer[].class);
//		model.addAttribute("lawyerList", responseEntity.getBody());
//		return "addArt.html";
//	}
//	
//	@PostMapping("/addArt")
//	public String processAddArt(@ModelAttribute Record record, RestTemplate restTemplate, 
//			@RequestParam String lawyerId) {
//		ResponseEntity<Lawyer> responseEntity = 
//			restTemplate.getForEntity(REST_URL_LAWYERS + lawyerId, Lawyer.class);
//		record.setEmployeeName(responseEntity.getBody().getName());
//		restTemplate.postForLocation(REST_URL_ART, record);
//		return "redirect:/addArt";
//	}
	
	@GetMapping("/delete/{id}")
	public String deleteAlbum(@PathVariable Long id, RestTemplate restTemplate) {
		restTemplate.delete(REST_URL_ALBUM + id);
		return "redirect:/viewAlbums";
	}
	
	@GetMapping("/editAlbum/{id}")
	public String loadEdit(@PathVariable Long id, Model model, 
			RestTemplate restTemplate) {
//		ResponseEntity<Art> responseEntityArt = 
//				restTemplate.getForEntity(REST_URL_ART + id, Record.class);
//		model.addAttribute("art", responseEntityArt.getBody());
		ResponseEntity<Album> responseEntity = 
				restTemplate.getForEntity(REST_URL_ALBUM + id, Album.class);
		model.addAttribute("album", responseEntity.getBody());
		return "editAlbum.html";
	}
	
	@PostMapping("/editAlbum")
	public String processEdit(@ModelAttribute Album album, 
			RestTemplate restTemplate) {
		// ResponseEntity<Album> responseEntity = 
		//		restTemplate.getForEntity(REST_URL_ALBUM + albumId, Album.class);
		// record.setName(responseEntity.getBody().getName());
		restTemplate.put(REST_URL_ALBUM + album.getId(), album);
		return "redirect:/viewAlbums";
	}

}
