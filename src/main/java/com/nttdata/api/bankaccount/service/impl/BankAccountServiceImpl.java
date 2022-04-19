package com.nttdata.api.bankaccount.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.api.bankaccount.dao.IBankAccountDAO;
import com.nttdata.api.bankaccount.document.BankAccount;
import com.nttdata.api.bankaccount.service.IBankAccountService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BankAccountServiceImpl implements IBankAccountService {
	
	private static final Logger LOGGER = LogManager.getLogger(BankAccountServiceImpl.class);

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
		
		return bankAccountDAO.findByCodeClientAndTypeAccountId(bankAccount.getCodeClient(),bankAccount.getTypeAccount().getId())
				.doOnSuccess(ex -> LOGGER.info("El cliente ya tiene una cuenta de este tipo"))
				.switchIfEmpty(bankAccountDAO.save(bankAccount))
				.onErrorResume(ex -> Mono.empty());
	}

	@Override
	public Mono<Void> delete(BankAccount bankAccount) {
		return bankAccountDAO.delete(bankAccount);
	}

	@Override
	public Flux<BankAccount> findByCodeClientAndTypeClientTypeClient(String codeClient, String typeClient) {
		// TODO Auto-generated method stub
		return bankAccountDAO.findByCodeClientAndTypeClientTypeClient(codeClient, typeClient);
	}
	
}
