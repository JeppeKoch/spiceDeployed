package dat.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import dat.entities.Spice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpiceDTO {
    @JsonProperty("id")
    private long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("flavor_profile")
    private String flavorProfile;
    @JsonProperty("description")
    private String description;


    public SpiceDTO(String name, String flavorProfile, String description) {
        this.name = name;
        this.flavorProfile = flavorProfile;
        this.description = description;
    }

    public SpiceDTO(Spice spice) {
        this.id = spice.getId();
        this.name = spice.getName();
        this.flavorProfile = spice.getFlavorProfile();
        this.description = spice.getDescription();
    }

}