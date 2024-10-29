package com.skylab.skyl_app.business.concretes;

import com.skylab.skyl_app.business.abstracts.UrlService;
import com.skylab.skyl_app.business.abstracts.UserService;
import com.skylab.skyl_app.core.exceptions.AliasAlreadyExistsException;
import com.skylab.skyl_app.core.exceptions.UrlNotFoundException;
import com.skylab.skyl_app.dataAccess.UrlDao;
import com.skylab.skyl_app.entities.Url;
import com.skylab.skyl_app.entities.dtos.UrlShortenDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UrlManager implements UrlService {

    private final UrlDao urlDao;

    private final UserService userService;

    public UrlManager(UrlDao urlDao, UserService userService) {
        this.urlDao = urlDao;
        this.userService = userService;
    }

    @Override
    public Url redirect(String alias) {
        Optional<Url> url = urlDao.findByAlias(alias);

        if (!url.isPresent()){
            throw new UrlNotFoundException(alias+ " named url not found");
        }

        url.get().setClickCount(url.get().getClickCount() + 1);

        return urlDao.save(url.get());
    }

    @Override
    public Url shorten(UrlShortenDto urlShortenDto) {

        var loggedInUser = userService.getLoggedInUser();

        if (CheckIfAliasExists(urlShortenDto.getAlias())) {
            throw new AliasAlreadyExistsException("Alias already exists");
        }

        if(urlShortenDto.getAlias().isEmpty()){
            urlShortenDto.setAlias(generateRandomAlias());
        }

        Url url = new Url();
        url.setAlias(urlShortenDto.getAlias());
        url.setUrl(urlShortenDto.getUrl());
        url.setClickCount(0);
        url.setCreatedBy(loggedInUser);

        return urlDao.save(url);
    }

    private String generateRandomAlias() {
        String alias = "";
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        for (int i = 0; i < 8; i++) {
            int index = (int) (characters.length() * Math.random());
            alias += characters.charAt(index);
        }

        if (CheckIfAliasExists(alias)) {
            return generateRandomAlias();
        }

        return alias;
    }

    private boolean CheckIfAliasExists(String alias) {
        return urlDao.findByAlias(alias).isPresent();
    }
}
