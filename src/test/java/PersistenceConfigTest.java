import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import re_lease.config.PersistenceConfig;
import re_lease.service_layer.user.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;

public class PersistenceConfigTest {

    @Test
    public void bootstrapAppFromJavaConfig() {
        ApplicationContext context = new AnnotationConfigApplicationContext(PersistenceConfig.class);
        assertThat(context).isNotNull();
        assertThat(context.getBean(UserRepository.class)).isNotNull();
    }

}
