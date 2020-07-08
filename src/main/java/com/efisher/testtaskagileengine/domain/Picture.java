package com.efisher.testtaskagileengine.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
* Class for representing concrete Picture object in JSON schema.
*
* */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Picture {
    @JsonProperty(value = "id")
    private String id;
    @JsonProperty(value = "cropped_picture")
    private String croppedPicture;
}
