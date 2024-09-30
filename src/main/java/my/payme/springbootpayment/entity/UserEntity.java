package my.payme.springbootpayment.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
@Entity
public class UserEntity extends BaseEntity {
    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    private String name;
    private boolean verified;
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<CardEntity> cards;

}

