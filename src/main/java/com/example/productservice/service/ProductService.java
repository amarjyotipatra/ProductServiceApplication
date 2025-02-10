package com.example.productservice.service;

import com.example.productservice.dto.ResponseDTO;
import com.example.productservice.model.Category;
import com.example.productservice.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductService {

    private RestTemplate  restTemplate;

    public Product getProductById(int id) {
        Product product = new Product();
        //fakestore api call using restTemplate
         ResponseEntity<ResponseDTO> fakeStoreResponse=
                 restTemplate.getForEntity("https://fakestoreapi.com/products/" + id, ResponseDTO.class);
         //Get Response
        ResponseDTO response = fakeStoreResponse.getBody();
        if(response==null) {
            throw new IllegalArgumentException("Product not found");
        }
        //convert response to product model
        product=convertFakeStoreResponseToProduct(response);
        return  product;
    }

    private Product convertFakeStoreResponseToProduct(ResponseDTO response) {
        Product product = new Product();
        Category category = new Category();

        category.setTitle(response.getCategory().getTitle());
        product.setId(response.getId());
        product.setCategory(category);
        product.setTitle(response.getTitle());
        product.setDescription(response.getDescription());
        product.setImageURL(response.getImageURL());
        return product;
    }
}
