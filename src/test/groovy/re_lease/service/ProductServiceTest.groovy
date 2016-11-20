package re_lease.service

import org.springframework.beans.factory.annotation.Autowired
import re_lease.domain.Product
import re_lease.domain.User
import re_lease.dto.PageParams
import re_lease.dto.ProductDTO
import re_lease.repository.ProductCustomRepository
import re_lease.repository.ProductRepository
import re_lease.repository.UserRepository
import re_lease.service.exceptions.NotPermittedException
import spock.lang.Shared

class ProductServiceTest extends BaseServiceTest {

    @Autowired
    ProductRepository productRepository

    @Autowired
    ProductCustomRepository productCustomRepository

    @Autowired
    UserRepository userRepository

    @Shared
    ProductService productService

    def setup() {
        productService = new ProductServiceImpl(
                productRepository,
                userRepository,
                productCustomRepository,
                securityContextService
        )
    }

    def "can delete product when have a permission"() {

        given:
        User user = userRepository.save(
                new User(login: "testLogin", password: "testPassword", email: "sometest@test.com")
        )
        Product product = productRepository.save(
                new Product(price: 666, title: "BestPen", description: "BBBest Pen", productLeaser: user)
        )
        signIn(user)

        when:
        productService.delete(product.id)

        then:
        productRepository.count() == 0

    }

    def "can not delete product when have no permission"() {

        given:
        User currentUser = userRepository.save(
                new User(login: "testLogin", password: "testPassword", email: "sometest@test.com")
        )
        User anotherUser = userRepository.save(
                new User(login: "test", password: "goodPassword", email: "django@yahoo.com")
        )
        Product product = productRepository.save(
                new Product(price: 666, title: "BestPen", description: "BBBest Pen", productLeaser: anotherUser)
        )
        signIn(currentUser)

        when:
        productService.delete(product.id)

        then:
        thrown(NotPermittedException)
        productRepository.count() == 1

    }

    def "can find posts by user when not sign in"() {

        given:
        User user = userRepository.save(
                new User(login: "testLogin", password: "testPassword", email: "sometest@test.com")
        )
        productRepository.save(
                new Product(price: 666, title: "BestPen", description: "BBBest Pen", productLeaser: user)
        )

        when:
        List<ProductDTO> products = productService.findByUser(user.id, new PageParams())

        then:
        products.size() == 1
        products.first().title == "BestPen"
        !products.first().isMyProduct

    }

    def "can find posts by user when signed in"() {

        given:
        User user = userRepository.save(
                new User(login: "testLogin", password: "testPassword", email: "sometest@test.com")
        )
        signIn(user)

        when:
        productRepository.save(
                new Product(price: 666, title: "BestPen", description: "BBBest Pen", productLeaser: user)
        )
        List<ProductDTO> products = productService.findByUser(user.id, new PageParams())

        then:
        products.first().isMyProduct

        when:
        User anotherUser = userRepository.save(
                new User(login: "test", password: "goodPassword", email: "django@yahoo.com")
        )
        productRepository.save(
                new Product(price: 4412, title: "BestPencil", description: " Not Best Pen", productLeaser: anotherUser)
        )
        List<ProductDTO> anotherProducts = productService.findByUser(anotherUser.id, new PageParams())

        then:
        !anotherProducts.first().isMyProduct

    }

    def "can find my products"() {

        given:
        User user = userRepository.save(
                new User(login: "testLogin", password: "testPassword", email: "sometest@test.com")
        )
        signIn(user)
        productRepository.save(
                new Product(price: 666, title: "BestPen", description: "BBBest Pen", productLeaser: user)
        )

        when:
        List<ProductDTO> products = productService.findMyProducts(new PageParams())

        then:
        products.size() == 1
        products.first().title == "BestPen"
        products.first().isMyProduct

    }

    def "can save my products"() {

        given:
        User user = userRepository.save(
                new User(login: "testLogin", password: "testPassword", email: "sometest@test.com")
        )
        signIn(user)
        Product product = new Product(price: 666, title: "BestPen", description: "BBBest Pen")

        when:
        productService.saveMyProduct(product)

        then:
        productRepository.findAll().size() == 1
        productRepository.findAll().first() == product
        product.productLeaser == user

    }

}
