package re_lease.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import re_lease.domain.Product
import re_lease.domain.User
import re_lease.dto.PageParams
import re_lease.dto.ProductDTO
import re_lease.dto.ProductPage
import re_lease.service.ProductService
import spock.mock.DetachedMockFactory

import static org.hamcrest.Matchers.hasSize
import static org.hamcrest.Matchers.is

@WebMvcTest(UserProductController)
class UserProductControllerTest extends BaseControllerTest {

    @TestConfiguration
    static class Config {

        @Bean
        ProductService productService(DetachedMockFactory factory) {
            return factory.Mock(ProductService)
        }

    }

    @Autowired
    ProductService productService;

    def "one can get a product list"() {

        given:"user did not sign in"
        User user = new User(id: 1, login: "testLogin", password: "goodPassword", email: "test@gmail.com")
        productService.findByUser(1, new PageParams(page: 1, size: 1)) >> (0..1).collect {
            Product product = new Product(
                    id: it, price: 1000 + it, title: "title${it}", description: "desc${it}", productLeaser: user
            )
            ProductDTO productDTO = ProductDTO.newInstance(product, false)
            List<ProductDTO> list = new ArrayList<ProductDTO>()
            list.add(productDTO)
            return ProductPage.instance(1L,1L,1L,list)
        }

        when:"user tries to get a product list"
        def response = perform(MockMvcRequestBuilders.get("/api/users/${user.id}/products/"))

        then:"user successfully receives a product list"
        response.andExpect(MockMvcResultMatchers.status().isOk())

    }

    def "one can get a product list of his own when is signed in"() {

        given:"user signed in"
        User user = signIn()
        productService.findMyProducts(new PageParams(page: 1, size: 1)) >> (0..1).collect {
            Product product = new Product(
                    id: it, price: 1000 + it, title: "title${it}", description: "desc${it}", productLeaser: user
            )
            ProductDTO productDTO = ProductDTO.newInstance(product, false)
            List<ProductDTO> list = new ArrayList<ProductDTO>()
            list.add(productDTO)
            return ProductPage.instance(1L,1L,1L,list)
        }

        when:"user tries to get a product list of his own"
        def response = perform(MockMvcRequestBuilders.get("/api/users/me/products"))

        then:"user successfully receives a product list of his own"
            response.andExpect(MockMvcResultMatchers.status().isOk())

    }

    def "one cannot list his own products when is not signed in"() {

        given:"undefined amount of products was in database"
        productService.findMyProducts(new PageParams()) >> []

        when:"user tries to get a list of his own products"
        def response = perform(MockMvcRequestBuilders.get("/api/users/me/products"))

        then:"user receives an exception of being unauthorized"
        response.andExpect(MockMvcResultMatchers.status().isUnauthorized())

    }

}
