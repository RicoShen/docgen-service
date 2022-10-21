package com.youland.doc.service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author: rico
 * @date: 2022/10/21
 **/
public interface ThirdService {

    String convertDocToPdf(MultipartFile file);

    String getPdf(String fileName);

    String getHtml(String fileName);

    String upload(MultipartFile file) throws IOException;
}
