package com.example.springboot_webflux_crud_mongodb;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.example.springboot_webflux_crud_mongodb.controller.ProductController;
import com.example.springboot_webflux_crud_mongodb.dto.ProductDTO;
import com.example.springboot_webflux_crud_mongodb.service.ProductService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@WebFluxTest(ProductController.class)
class SpringbootWebfluxCrudMongodbApplicationTests {

	@Autowired
	private WebTestClient webClient;

	@MockBean
	private ProductService productService;

	Mono<ProductDTO> productDTOMono;

	@BeforeEach
	public void setUp(){
		productDTOMono = Mono.just(ProductDTO.builder()
											 .id("1")
											 .name("Product 1")
											 .quantity(2)
											 .price(100.0)
											 .build());
	}
	

	@Test
	public void saveProductTest(){
		when(productService.saveProduct(productDTOMono)).thenReturn(productDTOMono);

		webClient.post()
				 .uri("/products")
				 .body(Mono.just(productDTOMono),ProductDTO.class)
				 .exchange()
				 .expectStatus().isOk();
		}

	@Test
	public void getProductTest(){
		when(productService.getProduct("1")).thenReturn(productDTOMono);

		Flux<ProductDTO> responseBody = webClient.get()
				 .uri("/products/1")
				 .exchange()
				 .expectStatus().isOk()
				 .returnResult(ProductDTO.class)	
				 .getResponseBody();

		StepVerifier.create(responseBody)
					.expectSubscription()
					.expectNext(productDTOMono.block())
					.verifyComplete();
	}


	@Test
	public void updateProductTest(){
		when(productService.updateProduct("1", productDTOMono)).thenReturn(productDTOMono);

		webClient.put()
				 .uri("/products/update/1")
				 .body(productDTOMono, ProductDTO.class)
				 .exchange()
				 .expectStatus().isOk();
				 
	}

	@Test
	public void deleteProductTest(){
		when(productService.deleteProduct("1")).thenReturn(Mono.empty());

		webClient.delete()
				 .uri("/products/delete/1")
				 .exchange()
				 .expectStatus().isOk();
	}

}
