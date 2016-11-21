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

    def "user can delete product when has a permission"() {

        given:"user had registered and had at least one product"
        User user = userRepository.save(
                new User(login: "testLogin", password: "testPassword", email: "sometest@test.com")
        )
        Product product = productRepository.save(
                new Product(price: 666, title: "BestPen", description: "BBBest Pen", productLeaser: user)
        )
        signIn(user)

        when:"user wants to delete his product"
        productService.delete(product.id)

        then:"product is successfully deleted"
        productRepository.count() == 0

    }

    def "user cannot delete product when has no permission"() {

        given:"some user had at least one product"
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

        when:"another user tries to delete not his product"
        productService.delete(product.id)

        then:"system throws Not Permitted Exception and does not delete product"
        thrown(NotPermittedException)
        productRepository.count() == 1

    }

    def "user can find products when is not signed in"() {

        given:"some user had at least one product"
        User user = userRepository.save(
                new User(login: "testLogin", password: "testPassword", email: "sometest@test.com")
        )
        productRepository.save(
                new Product(price: 666, title: "BestPen", description: "BBBest Pen", productLeaser: user)
        )

        when:"another user not signed in asks for all of this user products"
        List<ProductDTO> products = productService.findByUser(user.id, new PageParams())

        then:"the user successfully gets all information about products of another user"
        products.size() == 1
        products.first().title == "BestPen"
        !products.first().isMyProduct

    }

    def "user can find products when is signed in"() {

        given:"some user signed in"
        User user = userRepository.save(
                new User(login: "testLogin", password: "testPassword", email: "sometest@test.com")
        )
        signIn(user)

        when:"this user creates a new product and requests information about his products"
        productRepository.save(
                new Product(price: 666, title: "BestPen", description: "BBBest Pen", productLeaser: user)
        )
        List<ProductDTO> products = productService.findByUser(user.id, new PageParams())

        then:"the user successfully gets all information about his products"
        products.first().isMyProduct

        when:"the user requests all the products of another user who created at least one product."
        User anotherUser = userRepository.save(
                new User(login: "test", password: "goodPassword", email: "django@yahoo.com")
        )
        productRepository.save(
                new Product(price: 4412, title: "BestPencil", description: " Not Best Pen", productLeaser: anotherUser)
        )
        List<ProductDTO> anotherProducts = productService.findByUser(anotherUser.id, new PageParams())

        then:"user successfully gets all information about another user's products"
        !anotherProducts.first().isMyProduct

    }

    def "one can find his own products"() {

        given:"user had signed in and created a product"
        User user = userRepository.save(
                new User(login: "testLogin", password: "testPassword", email: "sometest@test.com")
        )
        signIn(user)
        productRepository.save(
                new Product(price: 666, title: "BestPen", description: "BBBest Pen", productLeaser: user)
        )

        when:"user requests all his own products"
        List<ProductDTO> products = productService.findMyProducts(new PageParams())

        then:"user successfully gets information about all of his own products"
        products.size() == 1
        products.first().title == "BestPen"
        products.first().isMyProduct

    }

    def "one can save his own products"() {

        given:"user had signed in and created a new product"
        User user = userRepository.save(
                new User(login: "testLogin", password: "testPassword", email: "sometest@test.com")
        )
        signIn(user)
        Product product = new Product(price: 666, title: "BestPen", description: "BBBest Pen")

        when:"user sends a request for saving his product"
        productService.saveMyProduct(product)

        then:"his product is successfully saved in database"
        productRepository.findAll().size() == 1
        productRepository.findAll().first() == product
        product.productLeaser == user

    }

}
