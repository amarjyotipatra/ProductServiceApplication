package com.example.productservice.service;

import com.example.productservice.dto.ResponseDTO;
import com.example.productservice.model.Category;
import com.example.productservice.model.Product;
import com.example.productservice.repository.ProductRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service("fakeStoreProductService")
public class FakeStoreProductService implements ProductService {

    private final RestTemplate  restTemplate;
    private ProductRepo productRepo;

    public FakeStoreProductService(RestTemplate restTemplate, ProductRepo productRepo) {
        this.restTemplate = restTemplate;
        this.productRepo = productRepo;
    }

    @Override
    public Product getProductById(int id) {
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

        product.setId((int) response.getId());
        product.setCategory(category);
        product.setDescription(response.getDescription());
        product.setImageURL(response.getImage());
        product.setTitle(response.getTitle());
        return product;
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products=new ArrayList<>();
        ResponseEntity<ResponseDTO[]> response=restTemplate.getForEntity("https://fakestoreapi.com/products", ResponseDTO[].class);
        for(ResponseDTO responseObj: Objects.requireNonNull(response.getBody())) {
            products.add(convertFakeStoreResponseToProduct(responseObj));
        }
        return products;
    }

    @Override
    public Product createProduct(String title, String imageURL, String category, String description) {
        ResponseDTO requestBody = new ResponseDTO();
        requestBody.setTitle(title);
        requestBody.setDescription(description);
        requestBody.setImage(imageURL);
        requestBody.setCategory(category);

        ResponseEntity<ResponseDTO> response=restTemplate.postForEntity("https://fakestoreapi.com/products", requestBody, ResponseDTO.class);
        return convertFakeStoreResponseToProduct(response.getBody());
    }

//    public Product updateProduct(Integer id,String title, String imageURL, String category, String description) {
//
//        return null;
//    }

    @Override
    public Product deleteProductById(int id) {
        // Step 1: Fetch the product before deletion (to return it later)
        Product productToDelete = getProductById(id);

        // Step 2: Make DELETE request to FakeStore API
        String url = "https://fakestoreapi.com/products/" + id;
        restTemplate.delete(url); // FakeStore API does not return a response body on DELETE

        // Step 3: Return the deleted product details
        return productToDelete;
    }

}
