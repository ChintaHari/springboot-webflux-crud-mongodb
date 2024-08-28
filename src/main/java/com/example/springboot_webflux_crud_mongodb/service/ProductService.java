package com.example.springboot_webflux_crud_mongodb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;

import com.example.springboot_webflux_crud_mongodb.dto.ProductDTO;
import com.example.springboot_webflux_crud_mongodb.repository.ProductRepository;
import com.example.springboot_webflux_crud_mongodb.utils.AppUtils;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;

    public Flux<ProductDTO> getAllProducts() {
        return productRepository
                    .findAll()
                    .map(AppUtils::productToProductDTO);
    }

    public Mono<ProductDTO> getProduct(String id) {
        return productRepository
                    .findById(id)
                    .map(AppUtils::productToProductDTO);
    }

    public Flux<ProductDTO> getProductsByRange(Double min, Double max) {
        return productRepository
                .findByPriceBetween(Range.closed(min, max))
                .map(AppUtils::productToProductDTO);
    }

    public Mono<ProductDTO> saveProduct(Mono<ProductDTO> productDTOMono) {
        return productDTOMono
                .map(AppUtils::productDtoToProduct)
                .flatMap(productRepository::insert)
                .map(AppUtils::productToProductDTO);       
    }

    public Mono<ProductDTO> updateProduct(String id, Mono<ProductDTO> productDTOMono){
        return productRepository.findById(id)
                                .flatMap(product -> productDTOMono
                                                    .map(AppUtils::productDtoToProduct)
                                                    .doOnNext(e -> e.setId(id)))
                                .flatMap(productRepository::save)
                                .map(AppUtils::productToProductDTO);
    }

    public Mono<Void> deleteProduct(String id) {
        return productRepository.deleteById(id);
    }

}
