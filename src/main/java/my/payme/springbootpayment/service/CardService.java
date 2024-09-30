package my.payme.springbootpayment.service;

import jakarta.security.auth.message.AuthException;
import my.payme.springbootpayment.dto.CreateCardDTO;
import my.payme.springbootpayment.dto.response.CardResponse;
import my.payme.springbootpayment.entity.CardEntity;
import my.payme.springbootpayment.entity.UserEntity;
import my.payme.springbootpayment.enumerators.CardType;
import my.payme.springbootpayment.exception.BaseException;
import my.payme.springbootpayment.repository.CardRepository;
import my.payme.springbootpayment.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class CardService implements BaseService<CardEntity,CardResponse,CreateCardDTO>{
    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private SecurityTokenService service;


    @Transactional
    public void create(CreateCardDTO createCardDTO, String token) throws AuthException {
        UserEntity user = service.decode(token);
        CardEntity card = modelMapper.map(createCardDTO, CardEntity.class);
        card.setOwner(user);
        cardRepository.save(card);
    }


    @Override
    public CardEntity mapRequestsToEntity(CreateCardDTO createCardDTO) {
        return modelMapper.map(createCardDTO,CardEntity.class);
    }


    @Override
    public CardResponse mapResponseToEntity(CardEntity entity) {
        return modelMapper.map(entity,CardResponse.class);
    }

//    public List<CardResponse> userCards(UUID userId) {
//        return cardRepository.findAll().stream().filter(cardEntity -> cardEntity.getOwner().getId().equals(userId))
//                .map(this::mapResponseToEntity)
//                .toList();
//    }



    public List<CardResponse> userCards(String token) throws BaseException, AuthException {
        UserEntity user = service.decode(token);
        return cardRepository.findAll().stream().filter(cardEntity ->
                cardEntity.getOwner().getId().equals(user.getId()))
                .map(this::mapResponseToEntity).toList();
    }



    public List<CardResponse> allCards(){
        return cardRepository.findAll().stream().map(this::mapResponseToEntity).toList();
    }


    public List<CardResponse> findCardsByType(CardType type){
       return cardRepository.findAllByTypeContains(type).stream()
               .map(this::mapResponseToEntity).toList();
    }

    @Transactional
    public void deleteCard(UUID cardId){
        cardRepository.deleteById(cardId);
    }


    @Transactional
    public void changeCardMain(String token,UUID cardId) throws BaseException, AuthException {
        UserEntity user = service.decode(token);
        CardEntity main = cardRepository.findByIsMain(true);
        main.setIsMain(false);
        Optional<CardEntity> card = cardRepository.findById(cardId);
        if(card.get().getOwner().getId().equals(user.getId())){
            card.get().setIsMain(true);
            cardRepository.save(card.get());
        }
        else {
            throw new AuthException("Invalid Card ID");
        }
    }




    public Double totalBalance(String token) throws AuthException {
        UserEntity user = service.decode(token);
        List<CardEntity> cards = cardRepository.findAllByOwner(user);
        return cards.stream().mapToDouble(CardEntity::getBalance).sum();
    }



    public String fillBalance(String token,UUID cardId,Double amount) throws AuthException,BaseException {
        CardEntity card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("card not found"));
        if(card.getOwner().getId().equals(service.decode(token).getId())){
            card.setBalance(card.getBalance()+amount);
          cardRepository.save(card);
        } else {
            throw new AuthException("invalid token");
        }
        return "fill balance";
    }



    public void updateCard(UUID cardId, String token, String name) throws AuthException,BaseException {
        CardEntity card = cardRepository.findById(cardId).orElseThrow(() ->
                new RuntimeException("card not found"));
         if(card.getOwner().getId().equals(service.decode(token).getId())){
             card.setName(name);
             cardRepository.save(card);

         } else {
             throw new AuthException("invalid token");
         }
     }


    public CardEntity findByCardNumber(String number) throws BaseException {
        return cardRepository.findByCardNumber(number)
                .orElseThrow(() -> new BaseException("Card Not Found",HttpStatus.BAD_REQUEST));
    }

    public void lowBalance(CardEntity card, Double amount) {
        card.setBalance(card.getBalance() - amount);
        cardRepository.save(card);
    }

    public void topBalance(CardEntity card, Double amount) {
        card.setBalance(card.getBalance() + amount);
        cardRepository.save(card);
    }
















































































}
