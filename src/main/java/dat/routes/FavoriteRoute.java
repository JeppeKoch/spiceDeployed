package dat.routes;

import dat.controllers.impl.FavoriteController;
import dat.controllers.impl.SpiceController;
import dat.daos.FavoriteDao;
import dat.daos.SpiceDao;
import dat.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;

import static io.javalin.apibuilder.ApiBuilder.*;

public class FavoriteRoute {

    private final FavoriteController favoriteController;

    public FavoriteRoute(EntityManagerFactory emf) {
        favoriteController = new FavoriteController(new FavoriteDao(emf));
    }


    protected EndpointGroup getRoutes() {

        return () -> {
            post("/{username}/favorites", favoriteController::create,Role.USER);
            get("/", favoriteController::readAll,Role.ANYONE);
            get("/{userId}/favorites", favoriteController::read,Role.USER);
            delete("/{userId}/favorites/{spiceId}", favoriteController::delete,Role.USER);
            post("/{username}/favorites/spices/{spiceId}", favoriteController::createSpiceFavorite,Role.USER);
            post("/{username}/favorites/cuisines/{cuisineId}", favoriteController::createCuisineFavorite,Role.USER);


        };
    }
}
