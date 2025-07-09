package com.skylab.skyl_app.business.concretes;

import com.skylab.skyl_app.business.abstracts.UrlService;
import com.skylab.skyl_app.business.abstracts.UserService;
import com.skylab.skyl_app.core.exceptions.AliasAlreadyExistsException;
import com.skylab.skyl_app.core.exceptions.UrlNotFoundException;
import com.skylab.skyl_app.dataAccess.UrlDao;
import com.skylab.skyl_app.entities.Role;
import com.skylab.skyl_app.entities.Url;
import com.skylab.skyl_app.entities.dtos.UrlShortenDto;
import org.springframework.stereotype.Service;

import java.util.List;
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
            /*
            return new Url(0, "https://www.youtube.com/watch?v=dQw4w9WgXcQ", "rickroll", 0,  new User(), null);

             */
           throw new UrlNotFoundException("Url not found");
        }

        url.get().setClickCount(url.get().getClickCount() + 1);

        return urlDao.save(url.get());
    }

    @Override
    public void deleteUrl(int urlId) {
        var loggedInUser = userService.getLoggedInUser();
        if (loggedInUser == null) {
            throw new UrlNotFoundException("User not found");
        }

        var urlResult = urlDao.findById(urlId);
        if (urlResult.isEmpty()) {
            throw new UrlNotFoundException("Url not found");
        }

        var url = urlResult.get();
        var isAdmin = loggedInUser.getAuthorities().contains(Role.ROLE_ADMIN);
        var isOwner = url.getCreatedBy().equals(loggedInUser);

        if (!isAdmin && !isOwner) {
            throw new UrlNotFoundException("You are not authorized to delete this url");
        }

        urlDao.delete(url);
    }

    @Override
    public Url shorten(UrlShortenDto urlShortenDto) {

        var loggedInUser = userService.getLoggedInUser();

        if (CheckIfAliasExists(urlShortenDto.getAlias())) {
            throw new AliasAlreadyExistsException("Alias already exists");
        }

        if(urlShortenDto.getAlias() == null || urlShortenDto.getAlias().isEmpty()) {
            urlShortenDto.setAlias(generateRandomAlias());
        }

        if (checkIfUrlHasProtocol(urlShortenDto.getUrl())) {
            urlShortenDto.setUrl("https://" + urlShortenDto.getUrl());
        }

        Url url = new Url();
        url.setAlias(urlShortenDto.getAlias());
        url.setUrl(urlShortenDto.getUrl());
        url.setClickCount(0);
        url.setCreatedBy(loggedInUser);

        return urlDao.save(url);
    }

    private boolean checkIfUrlHasProtocol(String url) {
        return !url.startsWith("http://") && !url.startsWith("https://");
    }

    @Override
    public List<Url> getAllUrls() {
        var urls = urlDao.findAll();

        if (urls.isEmpty()) {
            throw new UrlNotFoundException("No urls found");
        }

        return urls;
    }

    @Override
    public Url updateUrl(int urlId, UrlShortenDto urlShortenDto) {
        var loggedInUser = userService.getLoggedInUser();
        if (loggedInUser == null) {
            throw new UrlNotFoundException("User not found");
        }

        var urlResult = urlDao.findById(urlId);
        if (urlResult.isEmpty()) {
            throw new UrlNotFoundException("Url not found");
        }

        var url = urlResult.get();
        var isAdmin = loggedInUser.getAuthorities().contains(Role.ROLE_ADMIN);
        var isOwner = url.getCreatedBy().equals(loggedInUser);

        if (!isAdmin && !isOwner) {
            throw new UrlNotFoundException("You are not authorized to update this url");
        }

        if (CheckIfAliasExists(urlShortenDto.getAlias())) {
            throw new AliasAlreadyExistsException("Alias already exists");
        }

        url.setAlias(urlShortenDto.getAlias().isEmpty() ? url.getAlias() : urlShortenDto.getAlias());
        url.setUrl(urlShortenDto.getUrl().isEmpty() ? url.getUrl() : urlShortenDto.getUrl());

        return urlDao.save(url);
    }

    @Override
    public List<Url> getUserUrls() {
        var loggedInUser = userService.getLoggedInUser();

        var urls = urlDao.findAllByCreatedBy(loggedInUser);

        if (urls.isEmpty()) {
            throw new UrlNotFoundException("No urls found");
        }

        return urls;
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