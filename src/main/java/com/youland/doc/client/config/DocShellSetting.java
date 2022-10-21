package com.youland.doc.client.config;

import com.youland.doc.client.ClientBaseSetting;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author: rico
 * @date: 2022/10/21
 **/
@Data
@Configuration
@ConfigurationProperties(prefix = "third.url.docshell")
public class DocShellSetting extends ClientBaseSetting {
}
