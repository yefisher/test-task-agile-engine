package com.efisher.testtaskagileengine.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * Class for representing concrete Image object in JSON schema.
 *
 * */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Image {
    private String id;
    private String author;
    private String camera;
    private String tags;
    @JsonProperty(value = "cropped_picture")
    private String croppedPicture;
    @JsonProperty(value = "full_picture")
    private String fullPicture;
}
