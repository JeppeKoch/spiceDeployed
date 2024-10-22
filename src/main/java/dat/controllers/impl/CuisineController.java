package dat.controllers.impl;

import dat.controllers.IController;
import dat.dtos.CuisineDTO;
import dat.dtos.SpiceDTO;
import dat.entities.Cuisine;
import dat.entities.Spice;
import dat.security.exceptions.ApiException;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Purpose:
 *
 * @author: Jeppe Koch
 */
public class CuisineController implements IController<CuisineDTO, Integer> {

    private final Logger log = LoggerFactory.getLogger(CuisineController.class);

    @Override
    public void read(Context ctx) {
        try {
            int id = ctx.pathParamAsClass("id", Integer.class).get();

            CuisineDTO cuisineDTO = cuisineDao.read(id);
            ctx.res().setStatus(200);
            ctx.json(cuisineDTO, CuisineDTO.class);
        } catch (Exception e) {
            log.error("400{}",e.getMessage());
            throw new ApiException(400, e.getMessage());
        }

    }

    @Override
    public void readAll(Context ctx) {
        try {
            List<CuisineDTO> cuisineDTOS = dao.readAll();
            // response
            ctx.res().setStatus(200);
            ctx.json(cuisineDTOS, CuisineDTO.class);
        } catch (Exception e) {
            log.error("400{}",e.getMessage());
            throw new ApiException(400, e.getMessage());
        }
    }

    @Override
    public void create(Context ctx) {
        try {

            CuisineDTO cuisineDTO = ctx.bodyAsClass(CuisineDTO.class);
            ctx.status(HttpStatus.CREATED);
            ctx.json(cuisineDao.create(cuisineDTO));

            // == response ==
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
            CuisineDTO cuisineDTO = ctx.bodyAsClass(CuisineDTO.class);

            // == querying ==
            Cuisine cuisine = cuisineDao.getById(id);
            cuisineDao.update(cuisine, new Cuisine(cuisineDTO));

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
            int id = ctx.pathParamAsClass("id", Integer.class).get();
            // entity
            cuisineDao.delete(id);
            // response
            ctx.res().setStatus(204);
        } catch (Exception e) {
            log.error("400{}", e.getMessage());
            throw new ApiException(400, e.getMessage());
        }
    }
}
