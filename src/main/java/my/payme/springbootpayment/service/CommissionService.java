package my.payme.springbootpayment.service;

import jakarta.security.auth.message.AuthException;
import my.payme.springbootpayment.dto.CreateCommissionDTO;
import my.payme.springbootpayment.dto.response.CommissionResponse;
import my.payme.springbootpayment.entity.CommissionEntity;
import my.payme.springbootpayment.enumerators.CardType;
import my.payme.springbootpayment.repository.CommissionRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CommissionService implements BaseService<CommissionEntity, CommissionResponse, CreateCommissionDTO> {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CommissionRepo commissionRepo;


    @Override
    public CommissionEntity mapRequestsToEntity(CreateCommissionDTO createCommissionDTO) {
        return modelMapper.map(createCommissionDTO, CommissionEntity.class);
    }

    @Override
    public CommissionResponse mapResponseToEntity(CommissionEntity entity) {
        return modelMapper.map(entity, CommissionResponse.class);
    }

    public CommissionEntity findBySenderAndReceiver(CardType typeTo, CardType typeFrom) throws RuntimeException {
        return commissionRepo.findBySenderAndReceiver(typeTo, typeFrom)
                .orElseThrow(() -> new RuntimeException("Commission not found"));
    }







}
