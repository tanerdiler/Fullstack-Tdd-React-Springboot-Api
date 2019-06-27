package jugistanbul.tdd.port.rest;

			import lombok.Data;
			import lombok.EqualsAndHashCode;
			import lombok.NoArgsConstructor;

			import java.util.Objects;

			@Data
			@EqualsAndHashCode
			@NoArgsConstructor
			public class TransactionSearchRequest {

			    private AgentToSearch agent;

			    @Override
			    public boolean equals(Object o) {
			        if (this == o)
			            return true;
			        if (o == null || getClass() != o.getClass())
			            return false;
			        TransactionSearchRequest request = (TransactionSearchRequest) o;
			        return Objects.equals(agent, request.agent);
			    }

			    @Override
			    public int hashCode() {

			        return Objects.hash(agent);
			    }

			}