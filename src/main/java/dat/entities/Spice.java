package dat.entities;

import dat.dtos.SpiceDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "spice")
public class Spice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "spice_id", nullable = false, unique = true)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "flavor_profile", nullable = false)
    private String flavorProfile;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "cuisine_spice",
            joinColumns = {@JoinColumn(name = "spice_id", referencedColumnName = "spice_id")},
            inverseJoinColumns = {@JoinColumn(name = "cuisine_id", referencedColumnName = "cuisine_id")}
    )
    private Set<Cuisine> cuisineSet;

    @ManyToMany(mappedBy = "spices", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    private Set<Favorite> favorites = new HashSet<>();

    public Spice(String name, String description, String flavorProfile) {
        this.name = name;
        this.description = description;
        this.flavorProfile = flavorProfile;
    }

    public Spice(SpiceDTO spiceDTO){
        this.name = spiceDTO.getName();
        this.description = spiceDTO.getDescription();
        this.flavorProfile = spiceDTO.getFlavorProfile();
    }
}
