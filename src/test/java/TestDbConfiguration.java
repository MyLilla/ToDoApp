import com.javarush.todoapp.model.Task;
import com.javarush.todoapp.model.Teg;
import com.javarush.todoapp.model.User;
import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Properties;

import static java.lang.String.format;

public class TestDbConfiguration {

    @Getter
    private final SessionFactory sessionFactory;

    public TestDbConfiguration(PostgreSQLContainer postgreSQLContainer) {

        Properties properties = new Properties();

        properties.put(Environment.DRIVER, postgreSQLContainer.getDriverClassName());
        properties.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");
        properties.put(Environment.URL, format("jdbc:postgresql://%s:%s/%s",
                postgreSQLContainer.getContainerIpAddress(),
                postgreSQLContainer.getMappedPort(PostgreSQLContainer.POSTGRESQL_PORT),
                postgreSQLContainer.getDatabaseName()));
        properties.put(Environment.USER, postgreSQLContainer.getUsername());
        properties.put(Environment.PASS, postgreSQLContainer.getPassword());

        properties.put(Environment.SHOW_SQL, true);


        sessionFactory = new Configuration()
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Task.class)
                .addAnnotatedClass(Teg.class)
                .setProperties(properties)
                .buildSessionFactory();
    }
}
