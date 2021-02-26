package com.kirillov.gif_from_exchange_rates;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class GifFromExchangeRatesApplication {

    public static void main(String[] args) {
        SpringApplication.run(GifFromExchangeRatesApplication.class, args);
    }

}
