package com.youland.doc.service;

import com.youland.doc.dto.DocumentDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author: rico
 * @date: 2022/10/21
 **/
public interface ThirdService {

    String generateWordByTemplate(DocumentDTO documentDto);

    String convertDocToPdf(String fileName);

    String convertDocToPdf(MultipartFile file);

    String getPdf(String fileName);

    String getHtml(String fileName);

    String upload(MultipartFile file) throws IOException;
}
