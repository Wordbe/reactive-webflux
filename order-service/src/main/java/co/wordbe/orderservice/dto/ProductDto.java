package co.wordbe.orderservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDto {

    private String id;
    private String description;
    private Integer price;

    public ProductDto(String description, Integer price) {
        this.description = description;
        this.price = price;
    }
}
