package my.payme.springbootpayment.entity;
import jakarta.persistence.*;
import lombok.*;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "transactions")
@Entity
@Builder
public class TransactionEntity extends BaseEntity{
    @ManyToOne(fetch = FetchType.LAZY)
    private CardEntity fromCard;
    @ManyToOne(fetch = FetchType.LAZY)
    private CardEntity toCard;
    private double amount;
    private String code;
    private double commission;
}
