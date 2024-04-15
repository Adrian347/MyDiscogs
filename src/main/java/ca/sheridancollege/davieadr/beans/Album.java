package ca.sheridancollege.davieadr.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Album {

	private Long id;
	private String artist;
	private String title;
	private String releaseFormat;
	private String releaseYear;
	private String ownership;
	
}
