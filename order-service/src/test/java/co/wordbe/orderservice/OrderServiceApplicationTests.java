package co.wordbe.orderservice;

import co.wordbe.orderservice.client.ProductClient;
import co.wordbe.orderservice.client.UserClient;
import co.wordbe.orderservice.dto.ProductDto;
import co.wordbe.orderservice.dto.PurchaseOrderRequestDto;
import co.wordbe.orderservice.dto.PurchaseOrderResponseDto;
import co.wordbe.orderservice.dto.UserDto;
import co.wordbe.orderservice.service.OrderFulfillmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
class OrderServiceApplicationTests {

    @Autowired
    private UserClient userClient;

    @Autowired
    private ProductClient productClient;

    @Autowired
    private OrderFulfillmentService orderFulfillmentService;

    @Test
    void contextLoads() {
        Flux<Mono<PurchaseOrderResponseDto>> dtoFlux = Flux.zip(userClient.getAllUsers(), productClient.getAllProducts())
                .map(t -> buildDto(t.getT1(), t.getT2()))
                .map(dto -> orderFulfillmentService.processOrder(Mono.just(dto)))
                .doOnNext(System.out::println);

        StepVerifier.create(dtoFlux)
                .expectNextCount(4)
                .verifyComplete();
    }

    private PurchaseOrderRequestDto buildDto(UserDto userDto, ProductDto productDto) {
        PurchaseOrderRequestDto dto = new PurchaseOrderRequestDto();
        dto.setUserId(userDto.getId());
        dto.setProductId(productDto.getId());
        return dto;
    }

}
