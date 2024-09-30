package my.payme.springbootpayment.repository;
import my.payme.springbootpayment.dto.response.TransactionAllHistoryResponse;
import my.payme.springbootpayment.dto.response.TransactionHistoryReceivedResponse;
import my.payme.springbootpayment.dto.response.TransactionHistorySentResponse;
import my.payme.springbootpayment.entity.CardEntity;
import my.payme.springbootpayment.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactionRepo extends JpaRepository<TransactionEntity, UUID> {
    Optional<TransactionEntity> findByCode(String generationCode);

    @Query("select new my.payme.springbootpayment.dto.response.TransactionHistorySentResponse(t.fromCard.id,t.toCard.id, t.amount, t.createdDate) " +
            "from TransactionEntity t where t.fromCard.id = :cardId")
    List<TransactionHistorySentResponse> findSentTransactionsByCardId(UUID cardId);

    @Query("select new my.payme.springbootpayment.dto.response.TransactionHistoryReceivedResponse(t.fromCard.id, t.toCard.id, t.amount, t.createdDate) " +
            "from TransactionEntity t where t.toCard.id = :cardId")
    List<TransactionHistoryReceivedResponse> findReceivedTransactionsByCardId(UUID cardId);

    @Query("select new my.payme.springbootpayment.dto.response.TransactionAllHistoryResponse(t.fromCard.id, t.toCard.id, t.amount, t.createdDate, 'SENT') " +
            "from TransactionEntity t where t.fromCard.id = :cardId " +
            "union all " +
            "select new my.payme.springbootpayment.dto.response.TransactionAllHistoryResponse(t.fromCard.id, t.toCard.id, t.amount, t.createdDate, 'RECEIVED') " +
            "from TransactionEntity t where t.toCard.id = :cardId " +
            "order by t.createdDate desc")
    List<TransactionAllHistoryResponse> findAllTransactionsByCardId(UUID cardId);


    @Query("select new my.payme.springbootpayment.dto.response.TransactionAllHistoryResponse(t.fromCard.id, t.toCard.id, t.amount, t.createdDate, 'SENT') " +
            "from TransactionEntity t where t.fromCard.owner.id = :userId " +
            "union all " +
            "select new my.payme.springbootpayment.dto.response.TransactionAllHistoryResponse(t.fromCard.id, t.toCard.id, t.amount, t.createdDate, 'RECEIVED') " +
            "from TransactionEntity t where t.toCard.owner.id = :userId " +
            "order by t.createdDate desc "
    )
       List<TransactionAllHistoryResponse> findAllTransactionsByUserId(UUID userId);
}


