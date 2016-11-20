package re_lease.controller

import groovy.json.JsonOutput
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import re_lease.domain.Product
import re_lease.service.ProductService
import re_lease.service.exceptions.NotPermittedException
import spock.mock.DetachedMockFactory

import static org.hamcrest.Matchers.is

@WebMvcTest(ProductController)
class ProductControllerTest extends BaseControllerTest {

    @TestConfiguration
    static class Config {

        @Bean
        ProductService productService(DetachedMockFactory factory) {
            factory.Mock(ProductService, name: "productService")
        }

    }

    @Autowired
    ProductService productService

    def "can create a product when signed in"() {

        given:
        signIn()
        Integer price = 666
        String title = "Some title"
        String description = "Any description"

        when:
        def response = perform(
                MockMvcRequestBuilders.post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonOutput.toJson(price: price, title: title, description: description))
        )

        then:
        productService.saveMyProduct(_ as Product) >> new Product(price, title, description)
        with (response) {
            andExpect(MockMvcResultMatchers.status().isOk())
            andExpect(MockMvcResultMatchers.jsonPath('$.price').exists())
            andExpect(MockMvcResultMatchers.jsonPath('$.price', is(price)))
            andExpect(MockMvcResultMatchers.jsonPath('$.title').exists())
            andExpect(MockMvcResultMatchers.jsonPath('$.title', is(title)))
            andExpect(MockMvcResultMatchers.jsonPath('$.description').exists())
            andExpect(MockMvcResultMatchers.jsonPath('$.description', is(description)))
        }

    }

    def "can not create a product when not sign in"() {

        given:
        Integer price = 666
        String title = "Some title"
        String description = "Any description"

        when:
        def response = perform(
                MockMvcRequestBuilders.post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonOutput.toJson(price: price, title: title, description: description))
        )

        then:
        response.andExpect(MockMvcResultMatchers.status().isUnauthorized())

    }

    def "can delete a product when signed in"() {

        given:
        signIn()

        when:
        def response = perform(MockMvcRequestBuilders.delete("/api/products/1"))
        productService.delete(1)

        then:
        response.andExpect(MockMvcResultMatchers.status().isOk())

    }

    def "can not delete a product when not sign in"() {

        when:
        def response = perform(MockMvcRequestBuilders.delete("/api/products/1"))

        then:
        response.andExpect(MockMvcResultMatchers.status().isUnauthorized())

    }

    def "can not delete a product when have no permission"() {

        given:
        signIn()
        productService.delete(1) >> { throw new NotPermittedException("") }

        when:
        def response = perform(MockMvcRequestBuilders.delete("/api/products/1"))

        then:
        response.andExpect(MockMvcResultMatchers.status().isForbidden())

    }

}
