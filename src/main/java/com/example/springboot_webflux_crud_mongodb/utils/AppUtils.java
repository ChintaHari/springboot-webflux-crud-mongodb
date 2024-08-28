package com.example.springboot_webflux_crud_mongodb.utils;

import org.springframework.beans.BeanUtils;

import com.example.springboot_webflux_crud_mongodb.dto.ProductDTO;
import com.example.springboot_webflux_crud_mongodb.entity.Product;

public class AppUtils {

    public static ProductDTO productToProductDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        BeanUtils.copyProperties(product, productDTO);
        return productDTO;
    }

    public static Product productDtoToProduct(ProductDTO productDTO) {
        Product product = new Product();
        BeanUtils.copyProperties(productDTO, product);
        return product;
    }
    
}
