package jugistanbul.tdd.port.rest;

        import lombok.EqualsAndHashCode;
				import lombok.Getter;
				import lombok.NoArgsConstructor;

				@Getter
				@NoArgsConstructor
				@EqualsAndHashCode
				public class TransactionResponse {

				    private Integer id;

				    private String agent;

				    private String productName;

				    private String code;

				    private String state;

				    private Double price;

				    public TransactionResponse(Integer id, String agent, String code, String state, String productName, Double price) {
				        this.id = id;
				        this.agent = agent;
				        this.productName = productName;
				        this.code = code;
				        this.state = state;
				        this.price = price;
				    }
				}