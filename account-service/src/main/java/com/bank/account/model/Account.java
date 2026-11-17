package com.bank.account.model;

import java.math.BigDecimal;

public record Account(Long id, String holderName, String type, BigDecimal balance) {
}
