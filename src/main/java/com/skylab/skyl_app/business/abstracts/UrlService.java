package com.skylab.skyl_app.business.abstracts;

import com.skylab.skyl_app.entities.Url;
import com.skylab.skyl_app.entities.dtos.UrlShortenDto;

import java.util.Optional;

public interface UrlService {

    Url redirect(String alias);


    Url shorten(UrlShortenDto urlShortenDto);
}
