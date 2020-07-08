package com.efisher.testtaskagileengine;

import com.efisher.testtaskagileengine.domain.Image;
import com.efisher.testtaskagileengine.domain.Images;
import com.efisher.testtaskagileengine.domain.Picture;
import com.efisher.testtaskagileengine.domain.Pictures;
import com.efisher.testtaskagileengine.service.ImageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestTaskAgileEngineApplication.class)
public class CachingTest {

    @Autowired
    CacheManager cacheManager;

    @Autowired
    ImageService imageService;

    @BeforeEach
    void loadImages() {
        imageService.cacheBySchedule();
    }

    @Test
    void givenPictureThatShouldBeCashed_whenFindById_thenResultShouldBePutInCache() {
        Image image = imageService.getImageById("48f343496a9e6bdeb611");
        Image picture = getCachedPictures().get().getImages().get(0);
        assertEquals(image.getId(), picture.getId());
    }

    private Optional<Images> getCachedPictures() {
        return Optional.ofNullable(cacheManager.getCache("pictures")).map(cache -> cache.get(SimpleKey.EMPTY, Images.class));
    }

}
