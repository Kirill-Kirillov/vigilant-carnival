package com.kirillov.gif_from_exchange_rates.service;

import com.kirillov.gif_from_exchange_rates.dto.RatesDto;
import com.kirillov.gif_from_exchange_rates.dto.gif.GifDto;

import com.kirillov.gif_from_exchange_rates.feign.RatesClient;

import com.kirillov.gif_from_exchange_rates.feign.GifClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.Locale;


@Service
public class MainServiceImpl implements MainService{

    private final RatesClient ratesClient;
    private final GifClient gifClient;

    /** Поля для использования значений по умолчаний (если не были переданы в запросе) */
    private final String compareRates;
    private final String baseRates;


    @Autowired
    MainServiceImpl(RatesClient ratesClient,
                    GifClient gifClient,
                    @Value("${compareRates}") String compareRates,
                    @Value("${baseRates}") String baseRates) {
        this.ratesClient = ratesClient;
        this.gifClient = gifClient;
        this.compareRates = compareRates;
        this.baseRates = baseRates;
    }

    @Override
    public String getDiffBetweenTodayAndYesterdayRates(String baseRates, String compareRates) {


        if (baseRates==null) {
            baseRates=this.baseRates.toUpperCase();
        }
        else {
            baseRates=baseRates.toUpperCase();
        }


        if (compareRates==null) {
            compareRates=this.compareRates.toUpperCase();
        }
        else {
            compareRates=compareRates.toUpperCase();
        }

        RatesDto todayRates = ratesClient.getExchangeRates(baseRates, compareRates);

        if (todayRates.getRates().isEmpty()) {
            throw new NullPointerException("Пустое значение сравниваемой валюты, проверьте значение параметра compareRates");
        }

        String yesterdayDate = LocalDate.now().minusDays(1).toString();
        RatesDto yesterdayRates = ratesClient.getYesterdayRates(yesterdayDate, baseRates, compareRates);

        if (todayRates.getRates().get(compareRates)>yesterdayRates.getRates().get(compareRates)) {
             return "/api/gif/rich";
        }

        else if (todayRates.getRates().get(compareRates)<yesterdayRates.getRates().get(compareRates)) {
             return "/api/gif/broke";
        }

        else throw new IllegalStateException(
                "Курсы валют оказались одинаковыми!"
                        +" Возможно дело в том, что запрос сделан до открытия биржи, повторите запрос позже)");
    }

    public ResponseEntity<byte[]> getGif(String gifTag) {
        GifDto gifDto = gifClient.getGifByTag(gifTag);
        String url = gifDto.getData().getImages().get("original").getUrl();
        try {
            URI uri = new URI(url);
            System.out.println(uri);
            return new ResponseEntity<>(gifClient.getGifImage(uri), HttpStatus.OK);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
}
