package dat.entities;

import dat.dtos.CuisineDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Purpose:
 *
 * @author: Jeppe Koch
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cuisine")
public class Cuisine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cuisine_id", nullable = false, unique = true)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "flavor_profile", nullable = false)
    private String flavorProfile;

    @ManyToMany(mappedBy = "cuisines", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Favorite> favoriteSet;

    @ManyToMany(mappedBy = "cuisineSet", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Spice> spiceSet = new HashSet<>(); // = new HashSet<>();

    public Cuisine(String name, String description, String flavorProfile) {
        this.name = name;
        this.description = description;
        this.flavorProfile = flavorProfile;
    }

    public Cuisine(CuisineDTO cuisineDTO) {
        this.name = cuisineDTO.getName();
        this.description = cuisineDTO.getDescription();
        this.flavorProfile = cuisineDTO.getFlavorProfile();
        if(cuisineDTO.getSpices() != null) {
            cuisineDTO.getSpices().forEach(spiceDTO -> spiceSet.add(new Spice(spiceDTO)));
        }
    }


    public void setSpice(Set<Spice> spices) {
        if (spices != null) {
            this.spiceSet = spices;
            for (Spice spice : spices) {
                // Initialize the cuisineSet in the Spice entity if it's null
                if (spice.getCuisineSet() == null) {
                    spice.setCuisineSet(new HashSet<>());
                }
                // Add this Cuisine instance to the Spice's cuisineSet
                spice.getCuisineSet().add(this);
            }
        }
    }


}
