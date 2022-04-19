package com.nttdata.api.bankaccount.service;

import com.nttdata.api.bankaccount.document.BankAccount;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IBankAccountService {
	
	public Flux<BankAccount> findAll();
	
	public Mono<BankAccount> findById(String id);
	
	public Mono<BankAccount> save(BankAccount bankAccount);
	
	public Mono<Void> delete(BankAccount bankAccount);
	
	public Flux<BankAccount> findByCodeClientAndTypeClientTypeClient(String codeClient, String typeClient);

	public Mono<BankAccount> findByClientAndTypeAccount(String codeClient, Integer typeAccountId);
	
}
