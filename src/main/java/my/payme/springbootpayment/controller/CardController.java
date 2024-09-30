package my.payme.springbootpayment.controller;
import jakarta.security.auth.message.AuthException;
import jakarta.validation.Valid;
import my.payme.springbootpayment.dto.CreateCardDTO;
import my.payme.springbootpayment.dto.response.CardResponse;
import my.payme.springbootpayment.enumerators.CardType;
import my.payme.springbootpayment.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/card")
public class CardController {

    @Autowired
    private CardService cardService;

    @PostMapping("/create/{token}")
    public String create(@Valid @RequestBody CreateCardDTO card,
                         @PathVariable("token") String token) throws AuthException {
        cardService.create(card,token);
        return "card created";
    }




    @GetMapping("/user-cards/{token}")
    public Optional<List<CardResponse>> userCards(@PathVariable("token") String token) throws AuthException {
        List<CardResponse> cards = cardService.userCards(token);
        return Optional.of(cards);
    }



    @GetMapping("/all-cards")
    public Optional<List<CardResponse>> allCards(){
        List<CardResponse> cards = cardService.allCards();
        return Optional.of(cards);
    }

    @GetMapping("/find-card-by-type")
    public List<CardResponse> findByType(@RequestParam("type") CardType type){
        return cardService.findCardsByType(type).stream().toList();
    }

    @GetMapping("/delete/{cardId}")
    public String deleteCard(@PathVariable("cardId") UUID cardId){
        cardService.deleteCard(cardId);
        return "card deleted";
    }

    @PostMapping("/change-card/{cardId}/{token}")
    public String changeCardMain(@PathVariable("cardId") UUID cardId,
                                 @PathVariable("token") String token ) throws AuthException {
//        cardService.changeCardMain(cardId);
        cardService.changeCardMain(token,cardId);
        return "card changed";
    }

    @GetMapping("/show-total-balance/{token}")
    public Double totalBalance(@PathVariable("token") String token) throws AuthException {
        return cardService.totalBalance(token);
    }

    @PostMapping("/fill-balance/{token}/{cardId}")
    public String fillBalance(@PathVariable("token") String token,
                              @RequestParam("amount") Double amount,
                              @PathVariable("cardId") UUID cardId) throws AuthException {
       return cardService.fillBalance(token,cardId,amount);
    }

    @PostMapping("/update-card/{token}/{cardId}")
    public String updateCard(@PathVariable("token") String token,
                             @PathVariable("cardId") UUID cardId,
                             @RequestParam("name") String name) throws AuthException {
        cardService.updateCard(cardId,token,name);
        return "card updated";
    }









}
