package transactions.port.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import transactions.port.rest.service.TransactionService;

import javax.validation.Valid;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionSearchController {

    private final TransactionService service;

    @GetMapping
    public TransactionSearchResponse search(@RequestBody @Valid TransactionSearchRequest request) {
        return service.search(request);
    }
}