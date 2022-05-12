package com.nttdata.api.bankaccount.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Unwrapped.Nullable;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "bankaccount")
public class BankAccount {
	
	@Id
	private String accountNumber;
	private Integer typeClient;
	private String codeClient;
	private TypeAccount typeAccount;
	private Currency currency;
	@Nullable
	private Date membershipDate;
	@Nullable
	private Boolean main;
	private double balance;
	private Card card;

}
