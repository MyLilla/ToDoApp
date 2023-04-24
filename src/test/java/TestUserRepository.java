import com.javarush.todoapp.repositories.UserRepository;
import com.javarush.todoapp.repositories.hibernateImpl.UserHibernateRepository;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


@Testcontainers
public class TestUserRepository {

    private UserRepository userRepository;

    private static String SQL_CREATE_TABLES = """
            CREATE SCHEMA todo;
            CREATE TABLE todo.users (
              id BIGINT PRIMARY KEY,
              user_name VARCHAR(15) NOT NULL,
              login VARCHAR(20) NOT NULL,
              password VARCHAR(255) NOT NULL
            );
                        
            CREATE TABLE todo.tegs (
              id BIGINT PRIMARY KEY,
              title VARCHAR(10),
              color VARCHAR(10),
              user_id BIGINT
            );
                        
            CREATE TABLE todo.tasks (
              id BIGINT PRIMARY KEY,
              title VARCHAR(50),
              description TEXT,
              hours SMALLINT,
              status SMALLINT,
              priority SMALLINT,
              create_data TIMESTAMP,
              update_data TIMESTAMP,
              user_id BIGINT
            );
                        
            CREATE TABLE todo.teg_task (
            task_id BIGINT,
            teg_id BIGINT
            );
            """;

    String SQL_INSERT_USER = """
            INSERT INTO todo.users (id, user_name, login, password) 
            VALUES (1, 'Olga', 'user', '12345');
            """;

    @Container
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:14")
            .withDatabaseName("TestDB")
            .withUsername("postgres")
            .withPassword("postgres");

    @BeforeAll
    public static void startDb() {
        postgreSQLContainer.start();
    }

    @Test
    void testGetById() throws Exception {

        // подключение через jdbc и заполнение значений
        Connection conn = DriverManager.getConnection(
                postgreSQLContainer.getJdbcUrl(),
                postgreSQLContainer.getUsername(),
                postgreSQLContainer.getPassword());


        Statement statement = conn.createStatement();
        statement.execute(SQL_CREATE_TABLES);

        statement.executeUpdate(SQL_INSERT_USER);


        ResultSet resultSet = statement.executeQuery("SELECT * FROM todo.users WHERE id = 1");
        resultSet.next();
        String expResultName = resultSet.getString(2);

        // закрываю это подключение
        conn.close();

        // подключаюсь ч/з hibernate к тестовому контейнеру
        TestDbConfiguration testDb = new TestDbConfiguration(postgreSQLContainer);
        SessionFactory sessionFactory = testDb.getSessionFactory();
        // передаю в репозиторий SessionFactory для ТЕСТОВОЙ базы
        userRepository = new UserHibernateRepository(sessionFactory);

        // тут получаю ошибку
        String actResultName = userRepository.getById(1L).getUserName();
        System.out.println(actResultName);

        assertEquals(expResultName, actResultName);
    }

    @AfterAll
    public static void stopDb() {
        postgreSQLContainer.stop();
    }
}
