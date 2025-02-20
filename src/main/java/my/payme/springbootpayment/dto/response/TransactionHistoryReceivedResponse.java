package my.payme.springbootpayment.dto.response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransactionHistoryReceivedResponse {
    private UUID fromCardId;
    private UUID toCardId;
    private Double amount;
    private LocalDateTime transactionDate;
}
