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

    def "can list products"() {

        given:
        User user = new User(id: 1, login: "testLogin", password: "goodPassword", email: "test@gmail.com")
        productService.findByUser(1, new PageParams()) >> (0..1).collect {
            Product product = new Product(
                    id: it, price: 1000 + it, title: "title${it}", description: "desc${it}", productLeaser: user
            )
            return ProductDTO.newInstance(product, false)
        }

        when:
        def response = perform(MockMvcRequestBuilders.get("/api/users/${user.id}/products/"))

        then:
        with(response) {
            andExpect(MockMvcResultMatchers.status().isOk())
            andExpect(MockMvcResultMatchers.jsonPath('$', hasSize(2)))
            andExpect(MockMvcResultMatchers.jsonPath('$[0].title', is("title0")))
            andExpect(MockMvcResultMatchers.jsonPath('$[0].isMyProduct', is(false)))
            andExpect(MockMvcResultMatchers.jsonPath('$[0].user.login', is("testLogin")))
            andExpect(MockMvcResultMatchers.jsonPath('$[1].title', is("title1")))
            andExpect(MockMvcResultMatchers.jsonPath('$[1].price', is(1001)))
        }

    }

    def "can list my products when signed in"() {

        given:
        User user = signIn()
        productService.findMyProducts(new PageParams()) >> (0..1).collect {
            Product product = new Product(
                    id: it, price: 1000 + it, title: "title${it}", description: "desc${it}", productLeaser: user
            )
            return ProductDTO.newInstance(product, true)
        }

        when:
        def response = perform(MockMvcRequestBuilders.get("/api/users/me/products"))

        then:
        with(response) {
            andExpect(MockMvcResultMatchers.status().isOk())
            andExpect(MockMvcResultMatchers.jsonPath('$', hasSize(2)))
            andExpect(MockMvcResultMatchers.jsonPath('$[0].title', is("title0")))
            andExpect(MockMvcResultMatchers.jsonPath('$[0].isMyProduct', is(true)))
            andExpect(MockMvcResultMatchers.jsonPath('$[0].user.login', is(user.getUsername())))
            andExpect(MockMvcResultMatchers.jsonPath('$[1].title', is("title1")))
            andExpect(MockMvcResultMatchers.jsonPath('$[1].price', is(1001)))
        }

    }

    def "can not list my products when not signed in"() {

        given:
        productService.findMyProducts(new PageParams()) >> []

        when:
        def response = perform(MockMvcRequestBuilders.get("/api/users/me/products"))

        then:
        response.andExpect(MockMvcResultMatchers.status().isUnauthorized())

    }

}
