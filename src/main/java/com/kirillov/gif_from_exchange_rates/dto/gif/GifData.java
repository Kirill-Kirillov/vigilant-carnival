package com.kirillov.gif_from_exchange_rates.dto.gif;

import lombok.*;

import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GifData {

    private Map<String, GifImage> images;

}
