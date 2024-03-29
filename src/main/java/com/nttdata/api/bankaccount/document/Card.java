package com.nttdata.api.bankaccount.document;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Card {

	private String cardNumber;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date expiryDate;
	private TypeCard typeCard;
	
}
