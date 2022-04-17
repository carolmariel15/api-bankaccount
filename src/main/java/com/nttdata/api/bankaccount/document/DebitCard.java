package com.nttdata.api.bankaccount.document;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DebitCard {

	private String cardNumber;
	private Date expiryDate;
	private TypeCard typeCard;
	
}
