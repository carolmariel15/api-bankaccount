package com.nttdata.api.bankaccount.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.api.bankaccount.dao.IBankAccountDAO;
import com.nttdata.api.bankaccount.document.BankAccount;
import com.nttdata.api.bankaccount.service.IBankAccountService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BankAccountServiceImpl implements IBankAccountService {

	@Autowired
	private IBankAccountDAO bankAccountDAO;

	@Override
	public Flux<BankAccount> findAll() {
		return bankAccountDAO.findAll();
	}

	@Override
	public Mono<BankAccount> findById(String id) {
		return bankAccountDAO.findById(id);
	}

	@Override
	public Mono<BankAccount> save(BankAccount bankAccount) {
		return bankAccountDAO.save(bankAccount);
	}

	@Override
	public Mono<Void> delete(BankAccount bankAccount) {
		return bankAccountDAO.delete(bankAccount);
	}
	
}
