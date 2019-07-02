package jugistanbul.tdd.applicationlayer;

import jugistanbul.tdd.transactions.PurchasingAgent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.nonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionSearchInput {

    private PurchasingAgent agentToSearch;


    public boolean hasAgentToSearch() {
        return nonNull(agentToSearch);
    }
}
