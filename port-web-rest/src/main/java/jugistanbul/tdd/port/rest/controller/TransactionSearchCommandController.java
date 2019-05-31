package jugistanbul.tdd.port.rest.controller;

import jugistanbul.tdd.port.rest.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionSearchCommandController {

    private final TransactionService service;

    @GetMapping
    public TransactionSearchResponse save(@RequestBody @Valid TransactionSearchRequest request) {
        return service.search(request);
    }
}
