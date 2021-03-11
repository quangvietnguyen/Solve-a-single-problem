package cs.sheridancollege.beans;

import java.io.Serializable;

import lombok.*;
@AllArgsConstructor
@NoArgsConstructor
@Data

public class Users implements Serializable {
	
	private static final long serialVersionUID = 7755631757214327009L;
	private String username;
	private String password;
	private String fullname;
	private int age;
	private String email; 
	
}
