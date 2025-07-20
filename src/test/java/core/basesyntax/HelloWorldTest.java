package core.basesyntax;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserTest {
    private StorageDaoImpl storageDao;

    @BeforeEach
    void setUp() {
        storageDao = new StorageDaoImpl();
        Storage.people.clear();
    }

    @Test
    void checkPasswordAndLoginTooShort_throwsException() {
        User user = new User();
        user.setLogin("Serh");
        user.setPassword("1234567");

        assertThrows(RuntimeException.class, () -> {
            storageDao.add(user);
        });
    }

    @Test
    void checkUserAgeMoreThan18_throwsException() {
        User user = new User();
        user.setLogin("john_doe");
        user.setPassword("123123");
        user.setAge(15);

        assertThrows(RuntimeException.class, () -> {
            storageDao.add(user);
        });

    }

    @Test
    void checkUserLoginIsUnique_throwsException() {
        User firstUser = new User();
        firstUser.setLogin("Serezhka1");
        firstUser.setPassword("wwwwwww");
        firstUser.setAge(19);
        storageDao.add(firstUser);

        User secondUSer = new User();
        secondUSer.setLogin("Serezhka1");
        secondUSer.setPassword("wwwwwww");
        secondUSer.setAge(19);

        assertThrows(RuntimeException.class, () -> {
            storageDao.add(secondUSer);
        });
    }

    @Test
    void add_validUser_success() {
        User user = new User();
        user.setLogin("john_doe");
        user.setPassword("123123");
        user.setAge(18);

        User addedUser = storageDao.add(user);
        assertNotNull(addedUser.getId(), "ID має бути присвоєне");
        assertEquals("john_doe", addedUser.getLogin());
        assertEquals("123123", addedUser.getPassword());
        assertEquals(18, user.getAge());
        assertEquals(1, Storage.people.size(), "Має бути 1 користувач у базі");
    }

    @Test
    void get_userByLogin_success() {
        User user = new User();
        user.setLogin("Alicerman");
        user.setPassword("1234567");
        user.setAge(19);
        storageDao.add(user);

        User foundUser = storageDao.get("Alicerman");
        assertNotNull(foundUser);
        assertEquals("Alicerman", foundUser.getLogin());
    }

}
