package dat;


import dat.config.ApplicationConfig;
import dat.config.HibernateConfig;
import jakarta.persistence.EntityManagerFactory;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("spice");
    public static void main(String[] args) {
        ApplicationConfig.startServer(7070, emf);
    }
}