package dat.routes;

import dat.security.routes.SecurityRoutes;
import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;

import static io.javalin.apibuilder.ApiBuilder.path;

public class Routes {


    private final SpiceRoute spiceRoute;
    private final SecurityRoutes authRoute = new SecurityRoutes();
    private final FavoriteRoute favoriteRoute;
    private final CuisineRoute cuisineRoute;

    public Routes(EntityManagerFactory emf) {
        spiceRoute = new SpiceRoute(emf);
        favoriteRoute = new FavoriteRoute(emf);
        cuisineRoute = new CuisineRoute(emf);
    }

    public EndpointGroup getRoutes() {
        return () -> {
            path("/spices", spiceRoute.getRoutes());
            path("/auth", authRoute.getSecurityRoutes());

            //fordi vi finder users favorite spices og cuisines ghkjvhbkjblkjkibnkjn
            path("/users", favoriteRoute.getRoutes());
            path("/cuisines", cuisineRoute.getRoutes());

        };
    }
}
