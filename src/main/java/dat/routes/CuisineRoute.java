package dat.routes;

import dat.controllers.impl.CuisineController;
import dat.controllers.impl.SpiceController;
import dat.daos.CuisineDao;
import dat.daos.SpiceDao;
import dat.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;

import static io.javalin.apibuilder.ApiBuilder.*;

public class CuisineRoute {

    private final CuisineController cuisineController;

    public CuisineRoute(EntityManagerFactory emf) {
        cuisineController = new CuisineController(new CuisineDao(emf));
    }


    protected EndpointGroup getRoutes() {

        return () -> {
            //Usercontroller skal laves og metoderne skal laves
            post("/cuisine", cuisineController::create, Role.ADMIN);
            get("/", cuisineController::readAll, Role.ANYONE);
            get("/cuisine/{id}", cuisineController::read,Role.ANYONE);
            put("/cuisine/{id}", cuisineController::update,Role.ADMIN);
            delete("/cuisine/{id}", cuisineController::delete,Role.ADMIN);


        };
    }
}
