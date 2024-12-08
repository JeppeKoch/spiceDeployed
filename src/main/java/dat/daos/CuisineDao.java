package dat.daos;

import dat.controllers.impl.CuisineController;
import dat.dtos.CuisineDTO;
import dat.entities.Cuisine;
import dat.security.exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author laith kaseb
 **/


public class CuisineDao {

    private final Logger log = LoggerFactory.getLogger(CuisineController.class);

    private static EntityManagerFactory emf;

    public CuisineDao(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public static CuisineDTO read(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            Cuisine cuisine = em.find(Cuisine.class, id);
            return new CuisineDTO(cuisine);
        }
    }


    public List<CuisineDTO> readAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<CuisineDTO> query = em.createQuery("SELECT new dat.dtos.CuisineDTO(r) FROM Cuisine r", CuisineDTO.class);
            return query.getResultList();
        }
    }

    public CuisineDTO create(CuisineDTO cuisineDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Cuisine cuisine = new Cuisine(cuisineDTO);
            if (cuisineDTO.getName() != null) {
                cuisine = em.merge(cuisine);
            } else {

                em.persist(cuisine);
            }
            em.getTransaction().commit();
            return new CuisineDTO(cuisine);
        }
    }


    public void delete(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Cuisine cuisine = em.find(Cuisine.class, id);
            if (cuisine != null){
                em.remove(cuisine);
            }
            em.getTransaction().commit();
        }
    }


    public CuisineDTO update(Long id, CuisineDTO cuisineDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            Cuisine c = em.find(Cuisine.class, id);
            c.setName(cuisineDTO.getName());
            c.setDescription(cuisineDTO.getDescription());
            Cuisine newCuisine = em.merge(c);
            em.getTransaction().commit();
            return new CuisineDTO(newCuisine);
        }
    }

}
