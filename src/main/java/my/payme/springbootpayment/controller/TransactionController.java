package my.payme.springbootpayment.controller;
import jakarta.security.auth.message.AuthException;
import my.payme.springbootpayment.dto.CreateTransactionDTO;
import my.payme.springbootpayment.entity.UserEntity;
import my.payme.springbootpayment.service.SecurityTokenService;
import my.payme.springbootpayment.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private SecurityTokenService tokenService;

//    @PostMapping("/generate-code")
//    public String generateCode(@RequestParam("email") String email){
////        transactionService.confirmationCode(email);
//        return "sending";
//    }

    @PostMapping("/create-transaction/{token}")
    public ResponseEntity<String> create(@RequestBody CreateTransactionDTO dto,
                                         @PathVariable("token") String token) throws AuthException {
        UserEntity user = tokenService.decode(token);
        String emailCode = transactionService.create(dto, user);
        return ResponseEntity.ok(emailCode);
    }


    @PostMapping("/enter-code{code}/{token}")
    public String transaction(@RequestParam("code") String code,
                              @PathVariable("token") String token) throws AuthException {
        tokenService.decode(token);
        transactionService.checkcode(code);
        return "successfully";
    }










}
