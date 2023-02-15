package test6.test6.BusienessLayer;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users_db")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private String email;
    private String password;



    private String role;
    @OneToMany(mappedBy = "user")
    private List<Recipe> recipes = new ArrayList<>();

    public User(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

}
