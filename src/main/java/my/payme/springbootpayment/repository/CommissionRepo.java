package my.payme.springbootpayment.repository;

import my.payme.springbootpayment.entity.CommissionEntity;
import my.payme.springbootpayment.enumerators.CardType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CommissionRepo extends JpaRepository<CommissionEntity,UUID> {

    Optional<CommissionEntity> findBySenderAndReceiver(CardType sender, CardType receiver);

}