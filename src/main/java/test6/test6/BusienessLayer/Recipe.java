package test6.test6.BusienessLayer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "recipes_db")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recipe implements Serializable {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    int id;
    private String name;
    private String description;
    private List<String> ingredients;
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
