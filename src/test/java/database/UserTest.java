package database;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import re_lease.config.PersistenceConfig;
import re_lease.service_layer.enumerations.Gender;
import re_lease.service_layer.user.User;
import re_lease.service_layer.user.UserRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.*;

@ContextConfiguration(classes = PersistenceConfig.class)
public class UserTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    UserRepository userRepository;

    private List<User> getUserList() {
        return StreamSupport
                .stream(userRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Test
    public void findAllUsers() {
        executeSqlScript("data.sql", false);
        final List<User> userList = getUserList();
        assertThat(userList).hasSize(3);
    }

    @Test
    public void saveNewUser() {
        executeSqlScript("data.sql", false);
        final int initiallySize = getUserList().size();
        userRepository.save(new User("dasd", "das", "dqwd", "cxc", "qw", "cxc", Gender.FEMALE));
        assertThat(getUserList()).hasSize(initiallySize + 1);
    }

    @Test
    public void deleteUser() {
        executeSqlScript("data.sql", false);
        final int initiallySize = getUserList().size();
        final User user = new User("dasd", "das", "dqwd", "cxc", "qw", "cxc", Gender.FEMALE);
        userRepository.save(user);
        assertThat(getUserList()).hasSize(initiallySize + 1);
        userRepository.delete(user);
        assertThat(getUserList()).hasSize(initiallySize);
    }

}
