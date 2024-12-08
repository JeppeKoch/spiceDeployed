package dat.routes;

import dat.controllers.impl.SpiceController;
import dat.daos.SpiceDao;
import dat.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;

import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.apibuilder.ApiBuilder.delete;

public class SpiceRoute {

    private  SpiceController spiceController;

    public SpiceRoute(EntityManagerFactory emf) {
        spiceController = new SpiceController(new SpiceDao(emf));
    }

    protected EndpointGroup getRoutes() {

        return () -> {
            post("/spice", spiceController::create, Role.ADMIN);
            get("/", spiceController::readAll,Role.ANYONE);
            get("/spice/{id}", spiceController::read,Role.ANYONE);

            put("/spice/{id}", spiceController::update, Role.ADMIN);
            delete("/spice/{id}", spiceController::delete,Role.ADMIN);
        };
    }
}
