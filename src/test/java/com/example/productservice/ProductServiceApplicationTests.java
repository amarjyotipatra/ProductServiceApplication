package com.example.productservice;

import com.example.productservice.model.Product;
import com.example.productservice.repository.ProductRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class ProductServiceApplicationTests {

    @Autowired
    private ProductRepo _productRepo;

    @Test
    void contextLoads() {
    }

    @Test
    void testProductRepo(){
        Optional<Product> p = _productRepo.findById(1);
        System.out.println(p.get());
    }

}
