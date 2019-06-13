package jugistanbul.tdd.port.rest.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode
@NoArgsConstructor
public class TransactionSearchRequest {

    private AgentToSearch agent;

    public TransactionSearchRequest(AgentToSearch agent) {
        this.agent = agent;
    }

}
