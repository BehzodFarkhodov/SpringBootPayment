package my.payme.springbootpayment.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserCreatedDTO {
    private String email;
    private String password;
    private String name;
}




