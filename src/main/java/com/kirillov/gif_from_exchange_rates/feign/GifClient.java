package com.kirillov.gif_from_exchange_rates.feign;

import com.kirillov.gif_from_exchange_rates.dto.gif.GifDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.net.URI;

@FeignClient(name = "gif", url = "${gif.client.url}")
public interface GifClient {

    @GetMapping(value = "?api_key=${gif.client.apikey}&tag=${gifTag}&rating=g")
    GifDto getGifByTag(@PathVariable("gifTag") String gifTag);

    @GetMapping
    byte[] getGifImage (URI uri);
}
