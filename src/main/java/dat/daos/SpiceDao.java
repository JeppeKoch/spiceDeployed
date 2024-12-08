package dat.daos;

import dat.dtos.CuisineDTO;
import dat.dtos.FavoriteDTO;
import dat.dtos.SpiceDTO;
import dat.entities.Cuisine;
import dat.entities.Favorite;
import dat.entities.Spice;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;

/**
 * @author laith kaseb
 **/


public class SpiceDao {

    private static EntityManagerFactory emf;

    public SpiceDao(EntityManagerFactory emf) {
        this.emf = emf;
    }


    public static SpiceDTO read(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            Spice spice = em.find(Spice.class, id);
            return spice != null ? new SpiceDTO(spice) : null;
        }
    }

    public List<SpiceDTO> readAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<SpiceDTO> query = em.createQuery("SELECT new dat.dtos.SpiceDTO(r) FROM Spice r", SpiceDTO.class);
            return query.getResultList();
        }
    }

    public SpiceDTO readByName(String name){
        try (EntityManager em = emf.createEntityManager()) {
            Spice spice = em.find(Spice.class, name);
            return spice != null ? new SpiceDTO(spice) : null;
        }
    }

    public SpiceDTO create(SpiceDTO spiceDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Spice spice = new Spice(spiceDTO);
            if (spiceDTO.getName() != null) {
                spice = em.merge(spice);
            } else {
                em.persist(spice);
            }
            //em.persist(spice);
            em.getTransaction().commit();
            return new SpiceDTO(spice);
        }
    }


    public void delete(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Spice spice = em.find(Spice.class, id);
            if (spice != null){
                em.remove(spice);
            }
            em.getTransaction().commit();
        }
    }

    public SpiceDTO update(Long id, SpiceDTO spiceDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            Spice c = em.find(Spice.class,id);
            c.setName(spiceDTO.getName());
            c.setDescription(spiceDTO.getDescription());
            c.setFlavorProfile(spiceDTO.getFlavorProfile());
            Spice newspice = em.merge(c);
            em.getTransaction().commit();
            return new SpiceDTO(newspice);
        }
    }
}