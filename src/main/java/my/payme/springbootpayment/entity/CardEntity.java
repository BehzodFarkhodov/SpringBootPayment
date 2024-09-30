package my.payme.springbootpayment.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import my.payme.springbootpayment.enumerators.CardType;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "cards")
@Entity
public class CardEntity extends BaseEntity {
    private String name;
    private Double balance;
    private LocalDateTime expiryDate;
    @Column(unique = true)
    private String cardNumber;
    @Enumerated(EnumType.STRING)
    private CardType type;
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity owner;
    private Boolean isMain;


    @OneToMany(mappedBy = "fromCard", cascade = CascadeType.ALL)
    private List<TransactionEntity> transactionsSent;

    @OneToMany(mappedBy = "toCard", cascade = CascadeType.ALL)
    private List<TransactionEntity> transactionsReceived;


}
