package com.youland.doc.controller;


import com.google.common.collect.Lists;
import com.youland.commons.utils.UploadUtil;
import com.youland.doc.dto.DocumentDTO;
import com.youland.doc.dto.DocumentSource;
import com.youland.doc.service.ThirdService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: rico
 * @date: 2022/10/14
 **/
@RestController
@RequestMapping("/document")
@RequiredArgsConstructor
@Slf4j
public class DocumentController {

    private final ThirdService thirdService;

    @PostMapping("/generate")
    public ResponseEntity<String> genLoanDocument(@RequestBody DocumentDTO  documentDto){

        // generate docs from state.
        String fileName = thirdService.generateWordByTemplate(documentDto);
        String pdfFileName = thirdService.convertDocToPdf(fileName);

        // loanId ,sourceFileName(UUID.docx), genFileName(UUID.docx,UUID.pdf),
        //（1）上传附件至gendocs目录(UUID)
        //（2）把word转化为pdf
        //（3）把word和pdf通过邮件发送给loanOfficer（收件人邮箱)
        //（4）异步把附件上传至s3,并关联loanId; （mike提供接口，保存附件与loanId关联）
        String email = "rico@youland.com";
        thirdService.sendDocumentByTemplate(Lists.newArrayList(fileName, pdfFileName), DocumentSource.LOCAL, email);

        return  ResponseEntity.accepted().body(pdfFileName);
    }

    @PutMapping("/upload")
    public ResponseEntity<Void> uploadAttachmentFile(@RequestParam("file") MultipartFile file){



        return  ResponseEntity.accepted().build();

    }

    @PostMapping("/resend")
    public ResponseEntity<Void> resend(@RequestBody String loanId){
        //（1）根据loanId，取附件，mike提供接口查询附件
        //（2）发送邮件（取loanId 对应的收件人）

        return  ResponseEntity.accepted().build();
    }
}
