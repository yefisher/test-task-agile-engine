package com.efisher.testtaskagileengine.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/*
 * Class for representing concrete Pictures object in JSON schema.
 *
 * */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pictures {
    private List<Picture> pictures;
    private Long page;
    private Long pageCount;
    public boolean hasMore;
}
