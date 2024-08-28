package com.example.springboot_webflux_crud_mongodb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {
    public String id;
    public String name;
    public int quantity;
    public Double price;
}
