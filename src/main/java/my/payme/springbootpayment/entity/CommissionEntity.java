package my.payme.springbootpayment.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import my.payme.springbootpayment.enumerators.CardType;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "commissions")
@Entity
public class CommissionEntity extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private CardType receiver;

    @Enumerated(EnumType.STRING)
    private CardType sender;

    private Double amount;
}

