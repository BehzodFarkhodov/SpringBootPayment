package my.payme.springbootpayment.repository;

import my.payme.springbootpayment.entity.VerificationTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SecurityTokenRepo extends JpaRepository<VerificationTokenEntity, UUID> {
    Optional<VerificationTokenEntity> findByUserIdAndExpiryDateIsAfter(UUID userId, LocalDateTime currentTime);
}


