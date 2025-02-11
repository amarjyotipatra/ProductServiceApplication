package com.example.productservice.service;

import com.example.productservice.dto.ResponseDTO;
import com.example.productservice.model.Category;
import com.example.productservice.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductService {

    private final RestTemplate  restTemplate;

    public ProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Product getProductById(Integer id) {
        //fakestore api call using restTemplate
         ResponseEntity<ResponseDTO> fakeStoreResponse=
                 restTemplate.getForEntity("https://fakestoreapi.com/products/" + id, ResponseDTO.class);

        if (fakeStoreResponse.getStatusCode().is5xxServerError()) {
            throw new RuntimeException("API server error: " + fakeStoreResponse.getStatusCode());
        }
        //Get Response
        ResponseDTO response = fakeStoreResponse.getBody();
        if(response==null) {
            throw new IllegalArgumentException("Product not found");
        }
        System.out.println(response);
        //convert response to product model
        return convertFakeStoreResponseToProduct(response);
    }

    private Product convertFakeStoreResponseToProduct(ResponseDTO response) {
        Product product = new Product();
        Category category = new Category();
        category.setTitle(response.getCategory());

        product.setId(response.getId());
        product.setCategory(category);
        product.setDescription(response.getDescription());
        product.setImageURL(response.getImage());
        product.setTitle(response.getTitle());
        return product;
    }
}
