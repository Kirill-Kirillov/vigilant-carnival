package com.kirillov.gif_from_exchange_rates.feign;

import com.kirillov.gif_from_exchange_rates.dto.RatesDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@FeignClient(name = "exchangeRates", url = "${rates.client.url}")
public interface RatesClient {


    @GetMapping(value = "latest.json?app_id=${rates.client.apikey}&base={baseRates}&symbols={compareRates}")
    RatesDto getExchangeRates(@PathVariable("baseRates") String baseRates,
                              @PathVariable("compareRates") String compareRates);

    @GetMapping(value = "historical/{date}.json?app_id=${rates.client.apikey}&base={baseRates}&symbols={compareRates}")
    RatesDto getYesterdayRates(@PathVariable("date") String date,
                               @PathVariable("baseRates") String baseRates,
                               @PathVariable("compareRates") String compareRates);

}
