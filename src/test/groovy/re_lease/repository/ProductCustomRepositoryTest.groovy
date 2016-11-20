package re_lease.repository

import org.springframework.beans.factory.annotation.Autowired
import re_lease.domain.Product
import re_lease.domain.User
import re_lease.dto.PageParams

class ProductCustomRepositoryTest extends BaseRepositoryTest {

    @Autowired
    ProductRepository productRepository

    @Autowired
    ProductCustomRepository productCustomRepository

    @Autowired
    UserRepository userRepository

    def "can find products by user"() {

        given:
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

        when:
        List<ProductCustomRepository.Row> result =
                productCustomRepository.findByUser(user, new PageParams(sinceId: product2.id)).collect()

        then:
        result.size() == 1
        result.first().product == product3

        when:
        result = productCustomRepository.findByUser(user, new PageParams(maxId: product2.id)).collect()

        then:
        result.size() == 1
        result.first().product == product1

    }

}
