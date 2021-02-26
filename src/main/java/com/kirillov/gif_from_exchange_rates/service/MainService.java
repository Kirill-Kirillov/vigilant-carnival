package com.kirillov.gif_from_exchange_rates.service;


import org.springframework.http.ResponseEntity;

public interface MainService {
    String getDiffBetweenTodayAndYesterdayRates(String baseRates, String compareRates);

    ResponseEntity<byte[]> getGif (String gifTag);
}
