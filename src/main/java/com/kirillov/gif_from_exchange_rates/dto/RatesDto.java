package com.kirillov.gif_from_exchange_rates.dto;


import lombok.*;


import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RatesDto {

    private Map<String, Double> rates;

}
