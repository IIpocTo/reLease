package re_lease.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UsernameNotFoundException
import re_lease.domain.Product
import re_lease.domain.User
import re_lease.dto.PageParams
import javax.persistence.EntityNotFoundException

class ProductCustomRepositoryTest extends BaseRepositoryTest {

    @Autowired
    ProductRepository productRepository

    @Autowired
    ProductCustomRepository productCustomRepository

    @Autowired
    UserRepository userRepository

    def "user can find products"() {

        given:"user was registered AND some products were added"
        User user = userRepository.save(
                new User(login: "testLogin", password: "testPassword", email: "sometest@test.com")
        )
        Product product1 = productRepository.save(
                new Product(price: 312, title: "Title1", description: "Description1", productLeaser: user)
        )
        Product product2 = productRepository.save(
                new Product(price: 3132, title: "Title2", description: "Description2", productLeaser: user)
        )
        Product product3 = productRepository.save(
                new Product(price: 35, title: "Title3", description: "Description3", productLeaser: user)
        )

        when:"user tries to find products of a certain user"
        List<ProductCustomRepository.Row> result =
                productCustomRepository.findByUser(user, new PageParams(page: 1, size: 1)).collect()

        then:"user receives the first page of the product list"
        result.size() == 1
        result.first().product == product1

        when:"user tries to find products of a certain user"
        result = productCustomRepository.findByUser(user, new PageParams(page: 3, size: 1)).collect()

        then:"user receives the last page of the product list"
        result.size() == 1
        result.first().product == product3

    }

    def "system can load product by its id"() {

        given:"system saved a new Product to database"
        User user = userRepository.save(new User("user", "password", "ea@ya.ru"))
        Product product = new Product(12,"test","some description");
        product.productLeaser = user
        Product productCreated = productRepository.save(product)

        when:"system tries to get the Product by an existent id"
        ProductCustomRepository.Row productFound = productCustomRepository.findOne(productCreated.id).get()

        then:"system returns a matching Product"
        productFound.product == productCreated
        productFound.userStats.productCount == 1L

        when:"system tries to get Product by nonexistent id"
        Long notExistingId = productCreated.id + 1
        ProductCustomRepository.Row productNotFound = productCustomRepository.findOne(notExistingId).orElse(null)

        then:"system returns null"
        productNotFound == null

    }
}
