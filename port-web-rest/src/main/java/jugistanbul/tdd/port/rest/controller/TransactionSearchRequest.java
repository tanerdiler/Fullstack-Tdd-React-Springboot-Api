package jugistanbul.tdd.port.rest.controller;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class TransactionSearchRequest {

    private AgentToSearch agent;

}
