package my.payme.springbootpayment.service;

import jakarta.security.auth.message.AuthException;
import my.payme.springbootpayment.dto.CreateTransactionDTO;
import my.payme.springbootpayment.dto.response.TransactionAllHistoryResponse;
import my.payme.springbootpayment.dto.response.TransactionHistoryReceivedResponse;
import my.payme.springbootpayment.dto.response.TransactionHistorySentResponse;
import my.payme.springbootpayment.dto.response.TransactionResponse;
import my.payme.springbootpayment.entity.CardEntity;
import my.payme.springbootpayment.entity.CommissionEntity;
import my.payme.springbootpayment.entity.TransactionEntity;
import my.payme.springbootpayment.entity.UserEntity;
import my.payme.springbootpayment.exception.BaseException;
import my.payme.springbootpayment.repository.CardRepository;
import my.payme.springbootpayment.repository.TransactionRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class TransactionService implements BaseService<TransactionEntity, CreateTransactionDTO, TransactionResponse> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TransactionRepo transactionRepo;
    @Autowired
    private CardService cardService;

    @Autowired
    private CommissionService commissionService;
    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private SecurityTokenService service;

//    public String confirmationCode(String email) {
////        String generateCode = emailService.generateCode();
////        emailService.sendEmail(email,"Your confirmation code",generateCode);
//        System.out.println(getClass().getName() + " generateCode: " + generateCode);
//        TransactionEntity transactionEntity = new TransactionEntity();
//        transactionEntity.setCode(generateCode);
//        transactionRepo.save(transactionEntity);
//        return generateCode;
//    }


    @Override
    public TransactionEntity mapRequestsToEntity(TransactionResponse transactionResponse) {
        return modelMapper.map(transactionResponse, TransactionEntity.class);
    }

    @Override
    public CreateTransactionDTO mapResponseToEntity(TransactionEntity entity) {
        return modelMapper.map(entity, CreateTransactionDTO.class);
    }


//    public void createTransaction(CreateTransactionDTO card, double amount, String code) {
//        Optional<CardEntity> fromCard = cardRepository.findById(card.getFromCard());
//        Optional<CardEntity> toCard = cardRepository.findById(card.getToCard());
//
//        TransactionEntity transaction = new TransactionEntity();
//        if (fromCard.isPresent() && toCard.isPresent()){
//            if((fromCard.get().getBalance() >= amount) && transaction.getCode().equals(code)){
//                fromCard.get().setBalance(fromCard.get().getBalance() - amount);
//                cardRepository.save(fromCard.get());
//                toCard.get().setBalance(toCard.get().getBalance() + amount);
//                cardRepository.save(toCard.get());
//                transactionRepo.save(transaction);
//            }
//        }
//    }


    @Transactional
    public String create(CreateTransactionDTO createTransactionDTO, UserEntity user) throws AuthException {
        if (createTransactionDTO.getAmount() < 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        checkCommission(createTransactionDTO);
        String code = saveTransaction(createTransactionDTO);
        System.out.println(code);
//            sendEmail(user.getEmail(),code);
        return code;
    }


    private String saveTransaction(CreateTransactionDTO dto) throws AuthException {
        CardEntity toCard = cardService.findByCardNumber((dto.getToCard()));
        CardEntity fromCard = cardService.findByCardNumber(dto.getFromCard());
        CommissionEntity commission = commissionService.findBySenderAndReceiver(toCard.getType(), fromCard.getType());
        String code = generateCode();
        TransactionEntity transaction = TransactionEntity.builder()
                .toCard(toCard).fromCard(fromCard)
                .amount(dto.getAmount()).code(code)
                .commission(dto.getAmount() * (commission.getAmount() / 100.0))
                .code(code)
                .build();
        transactionRepo.save(transaction);
        return code;
    }


    public String generateCode() {
        return UUID.randomUUID().toString().substring(0, 6);
    }

    @Transactional
    public void checkcode(String code) {
        TransactionEntity transaction = transactionRepo.findByCode(code)
                .orElseThrow(() -> new BaseException("wrong code",HttpStatus.NOT_FOUND));
        CardEntity toCard = transaction.getToCard();
        CardEntity fromCard = transaction.getFromCard();
        CommissionEntity commission = commissionService.findBySenderAndReceiver(toCard.getType(), fromCard.getType());
        Double amount = transaction.getAmount() + transaction.getAmount() * (commission.getAmount() / 100.0);
        cardService.lowBalance(toCard, amount);
        cardService.topBalance(fromCard, transaction.getAmount());
    }


    private void checkCommission(CreateTransactionDTO dto) throws BaseException {
        CardEntity toCard = cardService.findByCardNumber(dto.getToCard());
        CardEntity fromCard = cardService.findByCardNumber(dto.getFromCard());
        CommissionEntity commission = commissionService.findBySenderAndReceiver(toCard.getType(), fromCard.getType());

        Double amount = dto.getAmount() + dto.getAmount() * (commission.getAmount() / 100.0);
        if (toCard.getBalance() < amount) {
            throw new BaseException("you don't have enough balance",HttpStatus.NOT_FOUND);
        }
    }


    public List<TransactionHistorySentResponse> getSentTransactions(UUID cardId) {
        return transactionRepo.findSentTransactionsByCardId(cardId);
    }

    public List<TransactionHistoryReceivedResponse> getReceivedTransactions(UUID cardId) {
        return transactionRepo.findReceivedTransactionsByCardId(cardId);
    }

    public List<TransactionAllHistoryResponse> getAllTransactions(UUID cardId) {
        return transactionRepo.findAllTransactionsByCardId(cardId);
    }

    public List<TransactionAllHistoryResponse> getAllTransactionByUser(UUID userId){
      return transactionRepo.findAllTransactionsByUserId(userId);
    }







}
