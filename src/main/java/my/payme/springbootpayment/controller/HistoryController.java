package my.payme.springbootpayment.controller;
import jakarta.security.auth.message.AuthException;
import my.payme.springbootpayment.dto.response.TransactionAllHistoryResponse;
import my.payme.springbootpayment.dto.response.TransactionHistoryReceivedResponse;
import my.payme.springbootpayment.dto.response.TransactionHistorySentResponse;
import my.payme.springbootpayment.entity.UserEntity;
import my.payme.springbootpayment.service.SecurityTokenService;
import my.payme.springbootpayment.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/history")
public class HistoryController {

   @Autowired
    private TransactionService transactionService;
   @Autowired
   private SecurityTokenService service;


    @GetMapping("/sent/{cardId}")
    public ResponseEntity<List<TransactionHistorySentResponse>> getSentTransactions(@PathVariable("cardId") UUID cardId) {
        List<TransactionHistorySentResponse> transactions = transactionService.getSentTransactions(cardId);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/received/{cardId}")
    public ResponseEntity<List<TransactionHistoryReceivedResponse>> getReceivedTransactions(@PathVariable("cardId") UUID cardId) {
        List<TransactionHistoryReceivedResponse> transactions = transactionService.getReceivedTransactions(cardId);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/all-history/{cardId}")
    public ResponseEntity<List<TransactionAllHistoryResponse>> getAllTransactions(@PathVariable("cardId") UUID cardId) {
        List<TransactionAllHistoryResponse> transactions = transactionService.getAllTransactions(cardId);
        return ResponseEntity.ok(transactions);
    }




    @GetMapping("/all-history-by-user/{token}")
    public ResponseEntity<List<TransactionAllHistoryResponse>> getAllTransactionsByUser(@PathVariable("token") String token) throws AuthException {
        UserEntity user = service.decode(token);
        List<TransactionAllHistoryResponse> transactions = transactionService.getAllTransactionByUser(user.getId());
        return ResponseEntity.ok(transactions);
    }










}
