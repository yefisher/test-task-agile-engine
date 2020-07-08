package com.efisher.testtaskagileengine.service;

import com.efisher.testtaskagileengine.ScheduledTokenRenewal;
import com.efisher.testtaskagileengine.domain.Image;
import com.efisher.testtaskagileengine.domain.Images;
import com.efisher.testtaskagileengine.domain.Picture;
import com.efisher.testtaskagileengine.domain.Pictures;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/*
 * General service class for image info manipulation.
 *
 * */

@Service
public class ImageService {

    private final String URI = "http://interview.agileengine.com/images/";

    public final RestTemplate restTemplate;


    @Autowired
    public ImageService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Pictures getPictures() {
        Pictures pictures = restTemplate.exchange(URI, HttpMethod.GET,
                new HttpEntity<>(createHeader(ScheduledTokenRenewal.token)), Pictures.class).getBody();

        return pictures;
    }

    public Image getImageById(final String id) {
        Image image = restTemplate.exchange(URI + id, HttpMethod.GET,
                new HttpEntity<>(createHeader(ScheduledTokenRenewal.token)), Image.class).getBody();

        return image;
    }

    public Pictures getImagesOnPage(final long page) {
        String preparedURI = URI + "?page=" + page;
        Pictures pictures = restTemplate.exchange(preparedURI, HttpMethod.GET,
                new HttpEntity<>(createHeader(ScheduledTokenRenewal.token)), Pictures.class).getBody();

        return pictures;
    }

    /*
     * Caching info once per day.
     *
     * */
    @Scheduled(fixedDelay = 86_400_000)
    @Cacheable("pictures")
    public Images cacheBySchedule() {
        return getEntirePictureInfoForCaching();
    }

    private Images getEntirePictureInfoForCaching() {
        long totalPages = getPages();
        Images images = new Images();
        List<Image> imageList = new ArrayList<>();

        for (int currentPage = 1; currentPage <= totalPages; currentPage++) {
            List<Picture> pictures = getImagesOnPage(currentPage).getPictures();
            pictures.forEach(picture -> imageList.add(getImageById(picture.getId())));
        }
        images.setImages(imageList);

        return images;
    }

    private long getPages() {
        Pictures pictures = getPictures();
        return pictures.getPageCount();
    }

    /*
     * Creating header for authorization process.
     *
     * */
    private HttpHeaders createHeader(String token) {
        return new HttpHeaders() {
            {
                String authHeader = "Bearer " + token;
                set("Authorization", authHeader);
            }
        };
    }
}
