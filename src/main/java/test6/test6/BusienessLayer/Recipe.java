package test6.test6.BusienessLayer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "recipes_db")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Recipe  {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    int id;
    private String name;
    private String description;
    @ElementCollection
    private List<String> ingredients;
    @ElementCollection
    private List<String> directions;
    private String category;
    @UpdateTimestamp
    private LocalDateTime date;
    @ManyToOne
    @JoinColumn(name = "user_email")
    private User user;

    @PrePersist
    private void setDate() {
        this.date = LocalDateTime.now();
    }

    @PostUpdate void updateLocalDateTime() {
        this.date = LocalDateTime.now();
    }


}
