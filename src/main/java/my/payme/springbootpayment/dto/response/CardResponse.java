package my.payme.springbootpayment.dto.response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import my.payme.springbootpayment.enumerators.CardType;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CardResponse {
    private String cardNumber;
    private Double balance;
    private CardType type;
    private LocalDateTime expiryDate;
}
