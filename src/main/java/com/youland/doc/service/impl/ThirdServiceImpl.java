package com.youland.doc.service.impl;

import com.deepoove.poi.XWPFTemplate;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.youland.doc.app.config.YoulandConfig;
import com.youland.doc.client.DocShellClient;
import com.youland.doc.dto.DocumentDTO;
import com.youland.doc.dto.DocumentSource;
import com.youland.doc.service.ThirdService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private final JavaMailSender mailSender;

    @Override
    public String generateWordByTemplate(DocumentDTO documentDto) {

        try {

        ClassPathResource file = new ClassPathResource("template/ca/construction_template.docx");
        XWPFTemplate template = XWPFTemplate.compile(file.getInputStream());

        Map datas = new HashMap();
        datas.put("Loan_ID", "20220915KL");
        datas.put("Property_Address", "37 Country Club Drive, Hayward, CA 94542");
        datas.put("CLOSING_DOCUMENT_DATE", "September 17, 2022");
        datas.put("TITLE_COMPANY_NAME", "North American Title Company");
        datas.put("TITLE_COMPANY_ADDRESS", "689 Portola Drive");
        datas.put("TITLE_COMPANY_CITY_STATE_ZIPCODE", "San Francisco, CA 94127");
        datas.put("SIGNING_USER_NAME", "Connie Choy");
        datas.put("SIGNING_USER_EMAIL", "cmchoy@nat.com");
        datas.put("TITLE_ORDER_NO", "56606-21-04011");
        datas.put("ESCROW_NO", "56606-21-04011");
        datas.put("TITLE_EFFECTIVE_DATE", "July 12, 2021");
        datas.put("PROPERTY_ADDRESS", "37 Country Club Drive, Hayward, CA 94542");
        datas.put("VESTING_CLAUSE_PRIVIEW", "KUN LIU LLC, a California limited liability company");
        datas.put("SIGNING_FIRST_NAME", "Connie");

        template.render(datas);
        XWPFDocument doc = template.getXWPFDocument();

        // for the file, set a new name.
        String fileExtension = Files.getFileExtension(file.getFilename());
        String uuid = UUID.randomUUID().toString();
        String newFileName = uuid.concat(".").concat(fileExtension);

        // output file
        OutputStream out = new FileOutputStream(youlandConfig.getDocsUrl() +"/"+ newFileName);
        doc.write(out);
        return newFileName;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    @Async
    public String sendDocumentByTemplate(List<String> fileUrls, DocumentSource documentSource, String email) {

        Map<String,InputStreamSource> attachments = Maps.newHashMap();

        try {

        if (DocumentSource.LOCAL == documentSource){
            for (String url: fileUrls) {
                FileUrlResource fileUrlResource = new FileUrlResource(youlandConfig.getDocsUrl().concat("/").concat(url));
                attachments.put(url, fileUrlResource);
            }
        }

        this.mailSender.send(mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setSubject("test");
            helper.setText("text",false);
            helper.setTo(email);
            helper.setFrom("do-not-reply-support@Youland.com");
            for (String key : attachments.keySet()) {
                helper.addAttachment(key, attachments.get(key));
            }

        });

        }catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String convertDocToPdf(String fileName) {
        docShellClient.convertWordToPdf(fileName);
        return Files.getNameWithoutExtension(fileName).concat(".pdf");
    }

    @Override
    public String convertDocToHtml(String fileName) {
        docShellClient.convertWordToHtml(fileName);
        return Files.getNameWithoutExtension(fileName).concat(".html");
    }

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
