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

    def "one can create a product when is signed in"() {

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

    def "one cannot create a product when is not sign in"() {

        given:"user did not sign in"
        Integer price = 666
        String title = "Some title"
        String description = "Any description"

        when:"user tries to create a product"
        def response = perform(
                MockMvcRequestBuilders.post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonOutput.toJson(price: price, title: title, description: description))
        )

        then:"user receives an exception of being unauthorized"
        response.andExpect(MockMvcResultMatchers.status().isUnauthorized())

    }

    def "one can delete a product when is signed in"() {

        given:"user signed in"
        signIn()

        when:"user tries to delete a product"
        def response = perform(MockMvcRequestBuilders.delete("/api/products/1"))
        productService.delete(1)

        then:"user successfully deletes the product"
        response.andExpect(MockMvcResultMatchers.status().isOk())

    }

    def "one cannot delete a product when is not sign in"() {

        given:"user did not sign in"

        when:"user tries to delete a product"
        def response = perform(MockMvcRequestBuilders.delete("/api/products/1"))

        then:"user receives an exception of being unauthorized"
        response.andExpect(MockMvcResultMatchers.status().isUnauthorized())

    }

    def "one cannot delete a product when has no permission"() {

        given:"user signed in"
        signIn()
        productService.delete(1) >> { throw new NotPermittedException("") }

        when:"user tries to delete a product without permission do to it"
        def response = perform(MockMvcRequestBuilders.delete("/api/products/1"))

        then:"user receives an exception of being forbidden to do that"
        response.andExpect(MockMvcResultMatchers.status().isForbidden())

    }

}
