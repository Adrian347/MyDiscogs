package ca.sheridancollege.davieadr.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Art {

	private Long id;
	private String name;
	private double price;
	private int quantity;
	private String employeeName;
	
}
