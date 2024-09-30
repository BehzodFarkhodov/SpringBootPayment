package my.payme.springbootpayment;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Base64;

@Configuration
public class config {

    @Bean
    public JavaMailSender getJavaMailSender() {
       return new JavaMailSenderImpl();
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setSkipNullEnabled(true);
        return modelMapper;
    }
    @Bean
    public Base64.Encoder encoder() {
        return Base64.getEncoder();
    }

    @Bean
    public Base64.Decoder decoder() {
        return Base64.getDecoder();
    }


}
