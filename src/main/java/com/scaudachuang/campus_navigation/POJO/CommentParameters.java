package com.scaudachuang.campus_navigation.POJO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentParameters {
    private String sortKey;
    private int buildingId;
    private int page;
    private int size;
}
