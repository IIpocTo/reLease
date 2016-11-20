package re_lease.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import re_lease.dto.PageParams;
import re_lease.dto.ProductDTO;
import re_lease.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserProductController {

    private final ProductService productService;

    @Autowired
    public UserProductController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{userId:\\d+}/products")
    public List<ProductDTO> list(@PathVariable("userId") Long userId, PageParams pageParams) {
        return productService.findByUser(userId, pageParams);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/me/products")
    public List<ProductDTO> list(PageParams pageParams) {
        return productService.findMyProducts(pageParams);
    }

}
