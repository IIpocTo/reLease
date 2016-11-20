package re_lease.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import re_lease.domain.Product;
import re_lease.dto.ProductParams;
import re_lease.service.ProductService;
import re_lease.service.exceptions.NotPermittedException;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Product create(@RequestBody ProductParams params) {
        return productService.saveMyProduct(params.toProduct());
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id) throws NotPermittedException {
        productService.delete(id);
    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ExceptionHandler(NotPermittedException.class)
    public void handleNoPermission() {}

}
