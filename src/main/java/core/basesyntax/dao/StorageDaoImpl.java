package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class StorageDaoImpl implements StorageDao {
    private static Long index = 0L;

    @Override
    public User add(User user) {
        if (user.getLogin().length() < 6) {
            throw new RuntimeException("Login should be longer than 6 characters");
        }
        if (user.getPassword().length() < 6) {
            throw new RuntimeException("Password should be longer than 6 characters");
        }
        if (user.getAge() < 18) {
            throw new RuntimeException("Only for users after 18 years old :(");
        }

        for (User person : Storage.people) {
            if (person.getLogin().equals(user.getLogin())) {
                throw new RuntimeException("There is already a user with such Login");
            }
        }

        user.setId(++index);
        Storage.people.add(user);
        return user;
    }

    @Override
    public User get(String login) {
        for (User user : Storage.people) {
            if (user.getLogin().equals(login)) {
                return user;
            }
        }
        return null;
    }
}
