package database;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import re_lease.config.PersistenceConfig;
import re_lease.service_layer.product.Product;
import re_lease.service_layer.product.ProductRepository;
import re_lease.service_layer.user.User;
import re_lease.service_layer.user.UserRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = PersistenceConfig.class)
public class ProductTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    private List<Product> getProductList() {
        return StreamSupport
                .stream(productRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    private List<User> getUserList() {
        return StreamSupport
                .stream(userRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }


    @Test
    public void findAllProducts() {
        executeSqlScript("data.sql", false);
        final List<Product> productList = getProductList();
        assertThat(productList).hasSize(2);
    }

    @Test
    public void saveNewProduct() {

        executeSqlScript("data.sql", false);
        final int initiallySize = getProductList().size();
        User productLeaser = getUserList().get(1);
        final Product newProduct = new Product(321, "dsafda", productLeaser);
        productRepository.save(newProduct);

        assertThat(getProductList()).hasSize(initiallySize + 1);
        assertThat(getUserList()).hasSize(3);
        assertThat(getUserList().get(1)).extracting(User::getProducts).hasSize(1);

    }

    @Test
    public void deleteUser() {

        executeSqlScript("data.sql", false);
        final int initiallySize = getProductList().size();
        User productLeaser = getUserList().get(1);
        final Product newProduct = new Product(432, "dsafda", productLeaser);
        productRepository.save(newProduct);

        assertThat(getProductList()).hasSize(initiallySize + 1);
        assertThat(getUserList()).hasSize(3);

        productRepository.delete(newProduct);

        assertThat(getProductList()).hasSize(initiallySize);
        assertThat(getUserList())
                .flatExtracting(User::getProducts)
                .flatExtracting(Product::getProductLeaser)
                .doesNotContain(productLeaser);

    }

}
