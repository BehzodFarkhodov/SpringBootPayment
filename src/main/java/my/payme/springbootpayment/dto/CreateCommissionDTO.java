package my.payme.springbootpayment.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import my.payme.springbootpayment.enumerators.CardType;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class CreateCommissionDTO {
    @Enumerated(EnumType.STRING)
    private CardType receiver;

    @Enumerated(EnumType.STRING)
    private CardType sender;

    private Double amount;

}
