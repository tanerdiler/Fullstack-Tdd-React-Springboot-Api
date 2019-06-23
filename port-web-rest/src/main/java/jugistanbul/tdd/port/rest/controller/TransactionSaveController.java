package jugistanbul.tdd.port.rest.controller;

import jugistanbul.tdd.port.rest.service.TransactionService;
import jugistanbul.tdd.transactions.TransactionState;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import javax.validation.Valid;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionSaveController {

    @PostMapping
    public TransactionSaveResponse save(@RequestBody @Valid TransactionSaveRequest request) {
        return service.save(request);
    }
}
