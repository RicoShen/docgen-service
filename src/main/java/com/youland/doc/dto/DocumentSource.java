package com.youland.doc.dto;

import lombok.Getter;

/**
 * @author: rico
 * @date: 2022/10/25
 **/
@Getter
public enum DocumentSource {

    S3("s3"),
    LOCAL("local");

    private final String value;

    DocumentSource(String value) {
        this.value = value;
    }
}
