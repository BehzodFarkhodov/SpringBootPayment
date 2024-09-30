package my.payme.springbootpayment.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import my.payme.springbootpayment.entity.UserEntity;
import my.payme.springbootpayment.enumerators.CardType;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class CreateCardDTO {
    private UserEntity owner;
    private String name;
    private double balance;
    private LocalDateTime expiryDate;
    private Integer cardNumber;
    @Enumerated(EnumType.STRING)
    private CardType type;
    private Boolean isMain;
}
