package com.nttdata.api.bankaccount.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientCredits {

    private List<BankCredit> listBankCredit;
    private List<CreditAccount> listCreditAccount;

}
