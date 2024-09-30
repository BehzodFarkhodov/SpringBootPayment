package my.payme.springbootpayment.service;

import jakarta.security.auth.message.AuthException;
import my.payme.springbootpayment.dto.LoginDTO;
import my.payme.springbootpayment.dto.UserCreatedDTO;
import my.payme.springbootpayment.dto.UserUpdateDTO;
import my.payme.springbootpayment.entity.UserEntity;
import my.payme.springbootpayment.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SecurityTokenService tokenService;

    @Autowired
    private EmailService emailService;

    @Transactional
    public void registerUser(UserCreatedDTO userCreatedDTO) {
        UserEntity map = modelMapper.map(userCreatedDTO, UserEntity.class);
        map.setVerified(false);
        userRepository.save(map);
        UserEntity user = userRepository.findByEmail(userCreatedDTO.getEmail()).
                orElseThrow(() -> new RuntimeException("User with email " + userCreatedDTO.getEmail() +
                        " already exists"));
//        emailService.sendVerificationEmail(user.getEmail());

    }


    public String loginUser(LoginDTO loginDTO) {
      UserEntity user = userRepository.findByEmail(loginDTO.getEmail())
              .orElseThrow(() -> new RuntimeException("User not found"));

      user.setVerified(true);
      userRepository.save(user);
         if(Objects.equals(user.getPassword(),loginDTO.getPassword())){
               return tokenService.generateToken(user);
         }
         throw new RuntimeException("Wrong password");
    }





//    String token = UUID.randomUUID().toString();
//        VerificationTokenEntity verificationToken = new VerificationTokenEntity(
//                token, LocalDateTime.now().plusMinutes(30),map);
//        verificationTokenRepo.save(verificationToken);
//        emailService.sendVerificationEmail(map.getEmail(),token);


    public String updateUser(UserUpdateDTO userUpdateDTO,String token) throws AuthException {
        UserEntity user = tokenService.decode(token);
        if(user != null){
            user.setName(userUpdateDTO.getName());
            user.setPassword(userUpdateDTO.getPassword());
            user.setEmail(userUpdateDTO.getEmail());
            userRepository.save(user);
        }
        return "user updated";

    }







}
