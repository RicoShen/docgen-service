package com.youland.doc.service.impl;

import com.google.common.io.Files;
import com.youland.doc.app.config.YoulandConfig;
import com.youland.doc.client.DocShellClient;
import com.youland.doc.service.ThirdService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * @author: rico
 * @date: 2022/10/21
 **/
@Service
@RequiredArgsConstructor
public class ThirdServiceImpl implements ThirdService {

    private final YoulandConfig youlandConfig;
    private final DocShellClient docShellClient;

    @Override
    public String convertDocToPdf(MultipartFile file) {

        // 1.generate *.docx to /gendocs/*
        // 2.convert
        // 3.for email, send .docx and .pdf
        // 4.upload .docx and .pdf to S3
        try {
            String newFileName = upload(file);
            docShellClient.convertWordToPdf(newFileName);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getPdf(String fileName) {

        return null;
    }

    @Override
    public String getHtml(String fileName) {
        return null;
    }

    @Override
    public String upload(MultipartFile file) throws IOException {

        FileOutputStream fileOutputStream = null;
        try {
            String fileExtension = Files.getFileExtension(file.getOriginalFilename());
            String uuid = UUID.randomUUID().toString();
            String newFileName = uuid.concat(".").concat(fileExtension);
            fileOutputStream = new FileOutputStream(youlandConfig.getDocsUrl() + "/"+ newFileName);
            fileOutputStream.write(file.getBytes());
            fileOutputStream.close();
            return newFileName;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fileOutputStream != null) {
                fileOutputStream.close();
            }
        }

        return null;
    }

}
