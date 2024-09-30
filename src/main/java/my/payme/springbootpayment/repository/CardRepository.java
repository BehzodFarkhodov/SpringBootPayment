package my.payme.springbootpayment.repository;

import my.payme.springbootpayment.entity.CardEntity;
import my.payme.springbootpayment.entity.TransactionEntity;
import my.payme.springbootpayment.entity.UserEntity;
import my.payme.springbootpayment.enumerators.CardType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CardRepository extends JpaRepository<CardEntity, UUID> {
   @Query(value = "from CardEntity  where type = :type")
    List<CardEntity> findAllByTypeContains(CardType type);

    List<CardEntity> findAllByOwner(UserEntity owner);

    @Query(value = "from CardEntity where isMain = :isMain")
    CardEntity findByIsMain(boolean isMain);

    Optional<CardEntity> findByCardNumber(String cardNumber);




}
