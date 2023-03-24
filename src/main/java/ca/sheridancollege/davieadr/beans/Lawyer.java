package ca.sheridancollege.davieadr.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lawyer {

	private Long id;
	private String name;
	
}
