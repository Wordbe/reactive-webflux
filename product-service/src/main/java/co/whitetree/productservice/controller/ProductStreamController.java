package co.whitetree.productservice.controller;

import co.whitetree.productservice.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
public class ProductStreamController {
    private final Flux<ProductDto> productDtoFlux;

    @GetMapping(value = "product/stream/{maxPrice}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ProductDto> getProductUpdates(@PathVariable Integer maxPrice) {
        return productDtoFlux.filter(productDto -> productDto.getPrice() <= maxPrice);
    }
}
