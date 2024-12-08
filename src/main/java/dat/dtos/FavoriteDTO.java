package dat.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import dat.entities.Favorite;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class FavoriteDTO {

    @JsonProperty("id")
    private long id;
    @JsonProperty("user_name")
    private String username;
    @JsonProperty("name")
    private String name;

    @JsonProperty("favorite")
    @EqualsAndHashCode.Exclude
    private Set<FavoriteDTO> spices = new HashSet<>();



    public FavoriteDTO(Favorite favorite) {
        this.id = favorite.getId();
        this.username = favorite.getUser().getUsername();
        this.name = favorite.getName();
    }

    public FavoriteDTO(String username, Long id) {
        this.username = username;
        this.id = id;
    }
}
