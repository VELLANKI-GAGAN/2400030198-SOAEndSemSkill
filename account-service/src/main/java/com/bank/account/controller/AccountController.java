/** 2400030198**/
package com.bank.account.controller;

import com.bank.account.model.Account;
import com.bank.account.service.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Internal route. Only reachable directly on the isolated port (8081).
 * Public clients must go through the gateway at /accounts/**.
 */
@RestController
@RequestMapping("/api/v1/internal/accounts")
public class AccountController {

    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @GetMapping
    public List<Account> list() {
        return service.findAll();
    }

    /** Echoes the path this service actually received - used to verify gateway rewriting. */
    @GetMapping("/ping")
    public Map<String, Object> ping(HttpServletRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("service", "account-service");
        body.put("receivedPath", request.getRequestURI());
        body.put("port", request.getLocalPort());
        return body;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> get(@PathVariable Long id) {
        return service.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Account> create(@RequestBody Account account) {
        return ResponseEntity.status(201).body(service.create(account));
    }
}
