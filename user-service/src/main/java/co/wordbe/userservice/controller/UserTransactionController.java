package co.wordbe.userservice.controller;

import co.wordbe.userservice.dto.TransactionRequestDto;
import co.wordbe.userservice.dto.TransactionResponseDto;
import co.wordbe.userservice.entity.UserTransaction;
import co.wordbe.userservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("user/transaction")
@RequiredArgsConstructor
public class UserTransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public Mono<TransactionResponseDto> createTransaction(@RequestBody Mono<TransactionRequestDto> requestDtoMono) {
        return requestDtoMono.flatMap(transactionService::createTransaction);
    }

    @GetMapping
    public Flux<UserTransaction> getByUserId(@RequestParam("userId") int userId) {
        return transactionService.getByUserId(userId);
    }
}
