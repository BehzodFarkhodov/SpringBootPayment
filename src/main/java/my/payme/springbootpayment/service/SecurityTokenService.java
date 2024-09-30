package my.payme.springbootpayment.service;

import jakarta.security.auth.message.AuthException;
import my.payme.springbootpayment.entity.UserEntity;
import my.payme.springbootpayment.entity.VerificationTokenEntity;
import my.payme.springbootpayment.repository.SecurityTokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.UUID;

@Service
public class SecurityTokenService {

    @Autowired
    private Base64.Encoder encoder;

    @Autowired
    private Base64.Decoder decoder;

    @Autowired
    private SecurityTokenRepo securityTokenRepo;
    @Value("${security.secret-key}")
    private String secretKey;




    public String generateToken(UserEntity user) {
        String tokenOrigin = user.getId().toString() + "T" + System.currentTimeMillis() + secretKey;
        String token = new String(encoder.encode(tokenOrigin.getBytes()));
        securityTokenRepo.save(new VerificationTokenEntity(token, calculateExpiryDate(), user));
        return token;
    }


    public UserEntity decode(String token) throws AuthException {
        String decodedToken = new String(decoder.decode(token));
        UUID userId = UUID.fromString(decodedToken.split("T")[0]);
        VerificationTokenEntity securityToken = securityTokenRepo.findByUserIdAndExpiryDateIsAfter(userId, LocalDateTime.now())
                .orElseThrow(() -> new AuthException("authorization exception, token expired or user not found"));
        return securityToken.getUser();
    }

    public LocalDateTime calculateExpiryDate() {
        return LocalDateTime.now().plus(30, ChronoUnit.MINUTES);
    }



}
