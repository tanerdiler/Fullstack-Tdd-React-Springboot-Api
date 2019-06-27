package jugistanbul.tdd.port.rest;

        import lombok.AllArgsConstructor;
        import lombok.Data;
        import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionSaveResponse {

    private Integer id;

    private String state;
}