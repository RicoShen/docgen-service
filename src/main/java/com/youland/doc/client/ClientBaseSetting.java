package com.youland.doc.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;

/**
 * @author: rico
 * @date: 2022/10/21
 **/
@Data
@NoArgsConstructor
public class ClientBaseSetting {

    // default setting
    public static final int DEFAULT_MAX_MEM_SIZE_BYTE = 2 * 1024 * 1024; //2 MB;
    public static final int DEFAULT_QUERY_TIMEOUT_MILLISEC = 60000; //60 sec
    public static final int DEFAULT_RETRY_MAX = 3;
    public static final int DEFAULT_RETRY_BACKOFF_MILLISEC = 500; //0.5 sec

    @JsonProperty(required = true)
    private String urlBase;

    private int maxMemSizeByte = DEFAULT_MAX_MEM_SIZE_BYTE;

    private Duration queryTimeout = Duration.ofMillis(DEFAULT_QUERY_TIMEOUT_MILLISEC);

    private int retryMax = DEFAULT_RETRY_MAX;

    private Duration retryBackoff = Duration.ofMillis(DEFAULT_RETRY_BACKOFF_MILLISEC);

}
