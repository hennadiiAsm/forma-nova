package com.formanova.user;

import com.formanova.user.api.dto.PaymentCardDto;
import com.formanova.user.api.mapper.PaymentCardMapper;
import com.formanova.user.persistence.entity.UserEntity;
import com.formanova.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Set;

@SpringBootApplication
@Slf4j
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

//    @Bean
    ApplicationRunner applicationRunner(UserService userService, PaymentCardMapper paymentCardMapper) {
        return args -> {
            UserEntity build = new UserEntity.Builder("a@z", "aaa", "A", "B", LocalDate.EPOCH)
                    .setPaymentCards(
                            Set.of(paymentCardMapper.toEntity(new PaymentCardDto("123", LocalDate.MAX, "1234")))
                    )
                    .setPhoneNumber("123")
                    .setCountry("asd")
                    .setCity("asd")
                    .build();

//            Uni.createFrom().item(build)
//                            .invoke(c -> System.out.println(c))
//                            .convert().with(UniReactorConverters.toMono())
//                            .subscribe(System.out::println);

            userService.save(build)
                    .subscribe(c -> log.warn(String.valueOf(c)));
        };
    }

}
