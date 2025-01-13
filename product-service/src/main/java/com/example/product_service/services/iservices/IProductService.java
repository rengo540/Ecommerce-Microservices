package com.example.product_service.services.iservices;


import com.example.product_service.dtos.AddProductDto;
import com.example.product_service.dtos.OneProductDto;
import com.example.product_service.dtos.ProductDto;
import com.example.product_service.dtos.ProductUpdateRequest;
import com.example.product_service.models.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IProductService {
    Product addProduct(AddProductDto product);

    Page<Product> getAllProducts(int pageNumber, int pageSize, String sortBy);

    Product getProductById(Long id);
    void deleteProductById(Long id);
    Product updateProduct(ProductUpdateRequest product, Long productId);
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category,String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrandAndName(String brand,String name);
    Long countProductsByBrandAndName(String brand,String name);
    ProductDto convertToDto(Product product) ;
    OneProductDto convertToOneProductDto(Product product);
    List<ProductDto> getConvertedProducts(List<Product> products);
}
