package co.wordbe.userservice.service;

import co.wordbe.userservice.dto.TransactionRequestDto;
import co.wordbe.userservice.dto.TransactionResponseDto;
import co.wordbe.userservice.dto.TransactionStatus;
import co.wordbe.userservice.entity.UserTransaction;
import co.wordbe.userservice.repository.UserRepository;
import co.wordbe.userservice.repository.UserTransactionRepository;
import co.wordbe.userservice.util.EntityDtoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final UserRepository userRepository;
    private final UserTransactionRepository userTransactionRepository;

    public Mono<TransactionResponseDto> createTransaction(final TransactionRequestDto requestDto) {
        return userRepository.updateUserBalance(requestDto.getUserId(), requestDto.getAmount())
                .filter(Boolean::booleanValue)
                .map(b -> EntityDtoUtil.toEntity(requestDto))
                .flatMap(userTransactionRepository::save)
                .map(ut -> EntityDtoUtil.toDto(requestDto, TransactionStatus.APPROVED))
                .defaultIfEmpty(EntityDtoUtil.toDto(requestDto, TransactionStatus.DECLINED));
    }

    public Flux<UserTransaction> getByUserId(int userId) {
        return userTransactionRepository.findByUserId(userId);
    }
}
