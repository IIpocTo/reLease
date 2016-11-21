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

        given:"registered user have at least one product"
        User user = userRepository.save(
                new User(login: "testLogin", password: "testPassword", email: "sometest@test.com")
        )
        Product product = productRepository.save(
                new Product(price: 666, title: "BestPen", description: "BBBest Pen", productLeaser: user)
        )
        signIn(user)

        when:"user want to delete his product"
        productService.delete(product.id)

        then:"product successfully deleted"
        productRepository.count() == 0

    }

    def "can not delete product when have no permission"() {

        given:"some user have at least one product"
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

        when:"another user trying to delete not his product"
        productService.delete(product.id)

        then:"system throwing Not Permitted Exception and not deleting product"
        thrown(NotPermittedException)
        productRepository.count() == 1

    }

    def "can find products by user when not sign in"() {

        given:"some user have at least one product"
        User user = userRepository.save(
                new User(login: "testLogin", password: "testPassword", email: "sometest@test.com")
        )
        productRepository.save(
                new Product(price: 666, title: "BestPen", description: "BBBest Pen", productLeaser: user)
        )

        when:"another user not sign in and asked for all of this user products"
        List<ProductDTO> products = productService.findByUser(user.id, new PageParams())

        then:"that user successfully get all information about products"
        products.size() == 1
        products.first().title == "BestPen"
        !products.first().isMyProduct

    }

    def "can find products by user when signed in"() {

        given:"some user signed in"
        User user = userRepository.save(
                new User(login: "testLogin", password: "testPassword", email: "sometest@test.com")
        )
        signIn(user)

        when:"this user create new product and requested his als his products"
        productRepository.save(
                new Product(price: 666, title: "BestPen", description: "BBBest Pen", productLeaser: user)
        )
        List<ProductDTO> products = productService.findByUser(user.id, new PageParams())

        then:"that user successfully get all information about his products"
        products.first().isMyProduct

        when:"another user also created his product and first user asked for all products of second user"
        User anotherUser = userRepository.save(
                new User(login: "test", password: "goodPassword", email: "django@yahoo.com")
        )
        productRepository.save(
                new Product(price: 4412, title: "BestPencil", description: " Not Best Pen", productLeaser: anotherUser)
        )
        List<ProductDTO> anotherProducts = productService.findByUser(anotherUser.id, new PageParams())

        then:"user successfully get all information about another user products"
        !anotherProducts.first().isMyProduct

    }

    def "can find my products"() {

        given:"user signed in and created his product"
        User user = userRepository.save(
                new User(login: "testLogin", password: "testPassword", email: "sometest@test.com")
        )
        signIn(user)
        productRepository.save(
                new Product(price: 666, title: "BestPen", description: "BBBest Pen", productLeaser: user)
        )

        when:"user asked for all of his products"
        List<ProductDTO> products = productService.findMyProducts(new PageParams())

        then:"user successfully get information about all of his products"
        products.size() == 1
        products.first().title == "BestPen"
        products.first().isMyProduct

    }

    def "can save my products"() {

        given:"user signed in and creating new product"
        User user = userRepository.save(
                new User(login: "testLogin", password: "testPassword", email: "sometest@test.com")
        )
        signIn(user)
        Product product = new Product(price: 666, title: "BestPen", description: "BBBest Pen")

        when:"user sending request for saving his product"
        productService.saveMyProduct(product)

        then:"his product successfully saved in database"
        productRepository.findAll().size() == 1
        productRepository.findAll().first() == product
        product.productLeaser == user

    }

}
