package com.nttdata.api.bankaccount.controller;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.WebExchangeBindException;

import com.nttdata.api.bankaccount.document.BankAccount;
import com.nttdata.api.bankaccount.service.IBankAccountService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/bankaccount")
public class BankAccountController {

	private static final Logger LOGGER = LogManager.getLogger(BankAccountController.class);

	@Autowired
	private IBankAccountService bankAccountService;

	@GetMapping
	public Mono<ResponseEntity<Flux<BankAccount>>> findAll() {
		LOGGER.info("metodo findAll: Retorna las cuentas bancarias");
		
		return Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(bankAccountService.findAll()));
	}

	@GetMapping("/{id}")
	public Mono<ResponseEntity<BankAccount>> findById(@PathVariable String id) {
		LOGGER.info("metodo findById: Buscar una cuenta bancaria por su ID");
		
		return bankAccountService.findById(id)
				.map(ba -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(ba))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@GetMapping("/typeClient/{codeClient}/{typeClient}")
	public Mono<ResponseEntity<Flux<BankAccount>>> findByCodeClientAndTypeClient(
			@PathVariable String codeClient, @PathVariable Integer typeClient) {
		LOGGER.info("metodo findByCodeClientAndTypeClient: Buscar una cuenta bancaria por codeClient y typeClient");
		
		return Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
				.body(bankAccountService.findByCodeClientAndTypeClient(codeClient, typeClient)));
	}

	@PostMapping
	public Mono<ResponseEntity<Map<String, Object>>> addBankAccount(
			@Valid @RequestBody Mono<BankAccount> monoBankAccount) {
		Map<String, Object> response = new HashMap<>();

		return monoBankAccount.flatMap(bankAccount -> {
			bankAccount.setMembershipDate(new Date());
			return bankAccountService.findByClientAndTypeAccount(bankAccount.getCodeClient(), bankAccount.getTypeAccount().getId())
			.map(ba -> {
				response.put("message", "El cliente ya tiene una cuenta de este tipo.");
				response.put("timestamp", new Date());

				return ResponseEntity.created(URI.create("/api/bankaccount/".concat(ba.getAccountNumber())))
						.contentType(MediaType.APPLICATION_JSON).body(response);
			}).switchIfEmpty(bankAccountService.save(bankAccount).map(ba -> {
				LOGGER.info("metodo addBankAccount: Agrega una cuenta bancaria");
				
				response.put("obj", ba);
				response.put("message", "Successfully saved.");
				response.put("timestamp", new Date());

				return ResponseEntity.created(URI.create("/api/bankaccount/".concat(ba.getAccountNumber())))

						.contentType(MediaType.APPLICATION_JSON).body(response);
			
			})).doOnSuccess(e->LOGGER.info("OK"));

		}).onErrorResume(t -> {
			return Mono.just(t).cast(WebExchangeBindException.class).flatMap(e -> Mono.just(e.getFieldErrors()))
					.flatMapMany(Flux::fromIterable)
					
					.map(fieldError -> "Field: " + fieldError.getField() + " " + fieldError.getDefaultMessage())
					.doOnError(e->LOGGER.error(e.getMessage()))
					.collectList().flatMap(list -> {
						response.put("errors", list);
						response.put("timestamp", new Date());
						response.put("status", HttpStatus.BAD_REQUEST.value());

						return Mono.just(ResponseEntity.badRequest().body(response));
					});
		}).doOnError(e -> LOGGER.warn("metodo addBankAccount: No se pudo agregar porque hubo errores"));
	}

	@PutMapping("/{id}")
	public Mono<ResponseEntity<BankAccount>> editBankAccount(@RequestBody BankAccount bankAccount,
			@PathVariable String id) {
		LOGGER.info("metodo editBankAccount: Edita una cuenta bancaria");
		
		return bankAccountService.findById(id).flatMap(ba -> {
			ba.setBalance(bankAccount.getBalance());
			return bankAccountService.save(ba);
		}).map(ba -> ResponseEntity.created(URI.create("/api/bankaccount/".concat(ba.getAccountNumber())))
				.contentType(MediaType.APPLICATION_JSON).body(ba)).defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> deleteBankAccount(@PathVariable String id) {
		LOGGER.info("metodo deleteBankAccount: Elimina una cuenta bancaria");
		
		return bankAccountService.findById(id).flatMap(ba -> {
			return bankAccountService.delete(ba).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));

		}).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
	}

}
