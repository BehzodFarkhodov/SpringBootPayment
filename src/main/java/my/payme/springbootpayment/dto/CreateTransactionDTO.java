package my.payme.springbootpayment.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateTransactionDTO {
    private String fromCard;
    private String toCard;
    private Double amount;
}

