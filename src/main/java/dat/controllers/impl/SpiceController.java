package dat.controllers.impl;

import dat.controllers.IController;
import dat.daos.SpiceDao;
import dat.dtos.SpiceDTO;
import dat.security.exceptions.ApiException;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 *Purpose:
 * @author: Jeppe Koch
 */
public class SpiceController implements IController<SpiceDTO, Integer> {

    private final Logger log = LoggerFactory.getLogger(SpiceController.class);
    private EntityManagerFactory emf;
    private SpiceDao spiceDao;


    public SpiceController(SpiceDao spiceDao) {
        this.spiceDao = spiceDao;
    }


    @Override
    public void read(Context ctx) {
        try {
            Long id = ctx.pathParamAsClass("id", Long.class).get();

            SpiceDTO spiceDTO = spiceDao.read(id);
            ctx.res().setStatus(200);
            ctx.json(spiceDTO, SpiceDTO.class);
        } catch (Exception e) {
            log.error("400{}",e.getMessage());
            throw new ApiException(400, e.getMessage());
        }

    }
    @Override
    public void readByName(Context ctx) {
        try {
            String name = ctx.pathParamAsClass("name", String.class).get();

            SpiceDTO spiceDTO = spiceDao.readByName(name);
            ctx.res().setStatus(200);
            ctx.json(spiceDTO, SpiceDTO.class);
        } catch (Exception e) {
            log.error("400{}",e.getMessage());
            throw new ApiException(400, e.getMessage());
        }
    }

    @Override
    public void readAll(Context ctx) {
        try {
            List<SpiceDTO> spiceDTOS = spiceDao.readAll();
            // response
            ctx.res().setStatus(200);
            ctx.json(spiceDTOS, SpiceDTO.class);
        } catch (Exception e) {
            log.error("400{}",e.getMessage());
            throw new ApiException(400, e.getMessage());
        }
    }

    @Override
    public void create(Context ctx) {
        try {

            SpiceDTO spiceDTO = ctx.bodyAsClass(SpiceDTO.class);
            ctx.status(HttpStatus.CREATED);
            ctx.json(spiceDao.create(spiceDTO));

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
            SpiceDTO spiceDTO = ctx.bodyAsClass(SpiceDTO.class);

            // == querying ==
            Long id = ctx.pathParamAsClass("id", long.class).get();
            spiceDao.update(id, spiceDTO);

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
            Long id = ctx.pathParamAsClass("id", Long.class).get();
            // entity
            spiceDao.delete(id);
            // response
            ctx.res().setStatus(204);
        } catch (Exception e) {
            log.error("400{}", e.getMessage());
            throw new ApiException(400, e.getMessage());
        }
    }



}
