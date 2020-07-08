package com.efisher.testtaskagileengine.controller;

import com.efisher.testtaskagileengine.domain.Image;
import com.efisher.testtaskagileengine.domain.Pictures;
import com.efisher.testtaskagileengine.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

/*
* General controller to retrieve data.
* */

@RestController
@RequestMapping("images")
public class ImageController {

    @Autowired
    public RestTemplate restTemplate;

    public final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Pictures> getImageList() {
        Pictures pictures = imageService.getPictures();

        return ResponseEntity.ok(pictures);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Image> getImage(@PathVariable final String id) {
        Image image = imageService.getImageById(id);

        assert image != null;
        return ResponseEntity.ok(image);
    }

    @GetMapping(params = {"page"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Pictures> getImageOnPage(@RequestParam(value = "page") final long page) {
        Pictures pictures = imageService.getImagesOnPage(page);

        return ResponseEntity.ok(pictures);
    }
}
