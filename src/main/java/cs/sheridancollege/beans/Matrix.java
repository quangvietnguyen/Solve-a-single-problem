package cs.sheridancollege.beans;

import java.io.Serializable;
import lombok.*;
@NoArgsConstructor
@AllArgsConstructor
@Data

public class Matrix implements Serializable{

	private static final long serialVersionUID = -8580849992747059017L;
	
	private int n;
	private String key;
}
