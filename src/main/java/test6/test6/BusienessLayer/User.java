package test6.test6.BusienessLayer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users_db")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private String email;
    private String password;
    private String role;
    @OneToMany(mappedBy = "user")
    private List<Recipe> recipes = new ArrayList<>();

    public User(String email, String encode, String authenticated) {
    }
}
