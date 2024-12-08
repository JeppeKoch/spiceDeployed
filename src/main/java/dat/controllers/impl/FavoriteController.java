package dat.controllers.impl;

import dat.controllers.IController;
import dat.daos.CuisineDao;
import dat.daos.FavoriteDao;
import dat.daos.SpiceDao;
import dat.dtos.CuisineDTO;
import dat.dtos.FavoriteDTO;
import dat.dtos.SpiceDTO;
import dat.security.daos.SecurityDAO;
import dat.security.entities.User;
import dat.security.exceptions.ApiException;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import jakarta.persistence.EntityManagerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Purpose:
 *
 * @author: Jeppe Koch
 */
public class FavoriteController implements IController<FavoriteDTO, Integer> {

    private final Logger log = LoggerFactory.getLogger(FavoriteController.class);
    private FavoriteDao favoriteDao;


    public FavoriteController(FavoriteDao favoriteDao){
        this.favoriteDao= favoriteDao;
    }

    @Override
    public void read(Context ctx) {
        try {
            Long id = Long.valueOf(ctx.pathParam("userId"));


            FavoriteDTO favoriteDTO = favoriteDao.read(id);
            ctx.res().setStatus(200);
            ctx.json(favoriteDTO, FavoriteDTO.class);
        } catch (Exception e) {
            log.error("400{}",e.getMessage());
            throw new ApiException(400, e.getMessage());
        }

    }

    @Override
    public void readByName(Context ctx) {

    }

    @Override
    public void readAll(Context ctx) {
        try {
            List<FavoriteDTO> favoriteDTOS = favoriteDao.readAll();
            // response
            ctx.res().setStatus(200);
            ctx.json(favoriteDTOS, FavoriteDTO.class);
        } catch (Exception e) {
            log.error("400{}",e.getMessage());
            throw new ApiException(400, e.getMessage());
        }
    }

    @Override
    public void create(Context ctx) {
        try {

            FavoriteDTO favoriteDTO = ctx.bodyAsClass(FavoriteDTO.class);
            String username = ctx.pathParam("username");
            User user = SecurityDAO.getUserFromUsername(username);


            ctx.status(HttpStatus.CREATED);
            ctx.json(favoriteDao.create(favoriteDTO, user));

        } catch (Exception e) {
            throw new ApiException(400, e.getMessage());
        }
    }
    public void createSpiceFavorite(Context ctx) {
        try {
            String username = ctx.pathParam("username");
            Long spiceId = Long.valueOf(ctx.pathParam("spiceId"));

            User user = SecurityDAO.getUserFromUsername(username);
            SpiceDTO spiceDTO = SpiceDao.read(spiceId);  // Ensure `spiceDao` has a read method for Spice

            List<FavoriteDTO> favoriteList = favoriteDao.getFavoriteFromUser(user);


            // Update favorites with the new spice
            FavoriteDTO updatedFavorite = favoriteDao.createSpiceFavoriteList(favoriteList, spiceDTO);

            // Set response status and return the updated favorite
            ctx.status(HttpStatus.CREATED);
            ctx.json(updatedFavorite);


            ctx.res().setStatus(201);
        } catch (Exception e) {
            log.error("400{}",e.getMessage());
            throw new ApiException(400, e.getMessage());

        }
    }


    @Override
    public void update(Context ctx) {
        try {
            long id = Long.parseLong(ctx.pathParam("id"));
            FavoriteDTO favoriteDTO = ctx.bodyAsClass(FavoriteDTO.class);

            // == querying ==
            Long favoriteId = ctx.pathParamAsClass("id", Long.class).get();
            favoriteDao.update(favoriteId, favoriteDTO);

            // == response ==
            ctx.res().setStatus(200);
        } catch (Exception e) {
            log.error("400{}",e.getMessage());
            throw new ApiException(400, e.getMessage());
        }
    }

    @Override
    public void delete(Context ctx) {
        try {
            Long id = ctx.pathParamAsClass("userId", Long.class).get();
            // entity
            favoriteDao.delete(id);
            // response
            ctx.res().setStatus(204);
        } catch (Exception e) {
            log.error("400{}", e.getMessage());
            throw new ApiException(400, e.getMessage());
        }
    }

    public void createCuisineFavorite(Context ctx) {
        try {
            String username = ctx.pathParam("username");
            Long cuisineId = Long.valueOf(ctx.pathParam("cuisineId"));

            User user = SecurityDAO.getUserFromUsername(username);
            CuisineDTO cuisineDTO = CuisineDao.read(cuisineId);  // Ensure `spiceDao` has a read method for Spice

            List<FavoriteDTO> favoriteList = favoriteDao.getFavoriteFromUser(user);

            // Update favorites with the new spice
            FavoriteDTO updatedFavorite = favoriteDao.createCuisineFavoriteList(favoriteList, cuisineDTO);

            // Set response status and return the updated favorite
            ctx.status(HttpStatus.CREATED);
            ctx.json(updatedFavorite);

            ctx.res().setStatus(201);
        } catch (Exception e) {
            log.error("400{}",e.getMessage());
            throw new ApiException(400, e.getMessage());
        }
    }
}
