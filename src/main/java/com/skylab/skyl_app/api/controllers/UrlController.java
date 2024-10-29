package com.skylab.skyl_app.api.controllers;

import com.skylab.skyl_app.business.abstracts.UrlService;
import com.skylab.skyl_app.entities.dtos.UrlShortenDto;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.springframework.http.HttpStatus.MOVED_PERMANENTLY;

@RestController
@RequestMapping("/")
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping("/{alias}")
    public ResponseEntity<?> handleRedirect(@PathVariable String alias, HttpServletResponse response) throws URISyntaxException, IOException {
        var result = urlService.redirect(alias);

        response.sendRedirect(result.getUrl());

        return new ResponseEntity<>(MOVED_PERMANENTLY);
    }

    @PostMapping("/shorten")
    public ResponseEntity<?> shortenUrl(@RequestBody UrlShortenDto urlShortenDto) {
        var result = urlService.shorten(urlShortenDto);

        return ResponseEntity.ok(result);
    }
}
