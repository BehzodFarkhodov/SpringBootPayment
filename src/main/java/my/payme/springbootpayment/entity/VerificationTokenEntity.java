package my.payme.springbootpayment.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "verification_tokens")
@Entity
public class VerificationTokenEntity extends BaseEntity {
    private String token;
    private LocalDateTime expiryDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.expiryDate);
    }


}

