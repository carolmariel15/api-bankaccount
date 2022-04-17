package com.nttdata.api.bankaccount.document;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "bank_account")
public class BankAccount {
	
	@Id
	private String accountNumber;
	private String codeClient;
	private TypeAccount typeAccount;
	private Currency currency;
	private Date membershipDate;
	private double balance;
	private DebitCard debitCard;

}
