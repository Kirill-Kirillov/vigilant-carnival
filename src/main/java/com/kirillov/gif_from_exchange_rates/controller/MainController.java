package com.kirillov.gif_from_exchange_rates.controller;


import com.kirillov.gif_from_exchange_rates.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;


@RestController
@RequestMapping("/api")
public class MainController {

    private final MainService mainService;

    @Autowired
    public MainController(MainService mainService) {
        this.mainService = mainService;
    }

    /**
     * Контроллер через сервис выясняет размер сегодняшнего и вчерашнего курса, сравнивает их
     * и по результатам сравнения перенаправляет на контроллер getImage c ключевым словом для поиска gif
     * baseRates - код базовой валюты (по умолчанию USD)
     * compareRates - код валюты для сравнения курсов (по умолчанию RUB)
     */

    @GetMapping("/diff-rates")
    public RedirectView getDiffFromRates(
            @RequestParam(required = false, name = "baseRates") String baseRates,
            @RequestParam(required = false, name = "compareRates") String compareRates){
        return new RedirectView(mainService.getDiffBetweenTodayAndYesterdayRates(baseRates, compareRates));
    }

    /** контроллер возвращает gif по тегу
     * gifTag - ключевое слово для поиска gif */

    @GetMapping(value = "/gif/{gifTag}", produces = MediaType.IMAGE_GIF_VALUE)
    public @ResponseBody ResponseEntity<byte[]> getImage(@PathVariable("gifTag") String gifTag) {
        return mainService.getGif(gifTag);
    }

}
