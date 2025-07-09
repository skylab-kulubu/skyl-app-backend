package com.skylab.skyl_app.api.controllers;

import com.skylab.skyl_app.business.abstracts.UrlService;
import com.skylab.skyl_app.entities.dtos.UrlShortenDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/urls")
public class UrlController {

    private final UrlService urlService;


    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping("/getAllUrls")
    public ResponseEntity<?> getAllUrls() {
        var result = urlService.getAllUrls();

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/deleteUrl/{urlId}")
    public ResponseEntity<?> deleteUrl(@PathVariable int urlId) {
        urlService.deleteUrl(urlId);

        return ResponseEntity.ok("Url deleted successfully");
    }


    @PutMapping("/updateUrl/{urlId}")
    public ResponseEntity<?> updateUrl(@PathVariable int urlId, @RequestBody UrlShortenDto urlShortenDto) {
       var result = urlService.updateUrl(urlId, urlShortenDto);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/getUserUrls")
    public ResponseEntity<?> getUserUrls() {
        var result = urlService.getUserUrls();

        return ResponseEntity.ok(result);
    }

    @GetMapping("/getOriginalUrl/{alias}")
    public ResponseEntity<?> getOriginalUrl(@PathVariable String alias) {
        var result = urlService.redirect(alias);

        return ResponseEntity.ok(result);
    }
}
