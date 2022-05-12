package com.nttdata.api.bankaccount.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Currency {
	
	private Integer id;
	private String description;

}
