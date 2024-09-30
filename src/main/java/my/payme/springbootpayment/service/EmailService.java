package my.payme.springbootpayment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EmailService {


    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}") private String sender;



//    public void sendVerificationEmail(String to) {
//        String verificationUrl = "http://yourdomain.com/verify?email=" + to;
//        String txt = "Click the linc for verification:\n" + verificationUrl;
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(to);
//        message.setSubject("Verify email");
//        message.setText(txt);
//        mailSender.send(message);
//    }
//
//    public String generateCode(){
//        return UUID.randomUUID().toString().substring(0,6);
//    }
//
//
//
//    public void sendCode(String email, String code){
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(email);
//        message.setSubject("Verify email");
//        message.setText(code);
//        mailSender.send(message);
//    }
//




}








