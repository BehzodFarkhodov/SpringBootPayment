package my.payme.springbootpayment.controller;
import jakarta.security.auth.message.AuthException;
import jakarta.validation.Valid;
import my.payme.springbootpayment.dto.LoginDTO;
import my.payme.springbootpayment.dto.UserCreatedDTO;
import my.payme.springbootpayment.dto.UserUpdateDTO;
import my.payme.springbootpayment.service.EmailService;
import my.payme.springbootpayment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;




    @PostMapping("/register")
    public String register(@Valid @RequestBody UserCreatedDTO user) {
        userService.registerUser(user);
        return "Url send your email!";
    }

    @GetMapping("/login")
    public String login(
                        @RequestBody LoginDTO loginDTO) {
         userService.loginUser(loginDTO);
         return "login successful!";

    }

    @PostMapping("/update/{token}")
    public String update(@RequestBody UserUpdateDTO dto,
                         @PathVariable("token") String token) throws AuthException {
        userService.updateUser(dto,token);
        return "Update successful!";
    }













}
