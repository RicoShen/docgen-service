package com.youland.doc;

import com.deepoove.poi.XWPFTemplate;
import com.youland.doc.util.DocTimeUtil;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class DocApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void replaceByTemplate() {

        try {
            ClassPathResource file = new ClassPathResource("template.docx");
            XWPFTemplate template = XWPFTemplate.compile(file.getInputStream());
            // 段落内容替换，表格内容替换、页脚替换
            Map datas = new HashMap();
            datas.put("Loan_ID", "20220915KL"); // footer
            datas.put("Property_Address", "37 Country Club Drive, Hayward, CA 94542"); // footer
            datas.put("order_no", "56606-21-04011"); // 段落中的内容
            datas.put("email", "richard@youland.com"); // 段落中的内容
            datas.put("leander_fee", "$22,500.00"); // 表格内容
            datas.put("lender_processing_fee", "$1,695.00"); //表格内容
            template.render(datas);

            XWPFDocument doc = template.getXWPFDocument();
            OutputStream out = new FileOutputStream("docs/template_replace.docx");
            doc.write(out);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void docDate() {
        String docDate = DocTimeUtil.getDocDate(Instant.now());
        System.out.println("docDate : " + docDate);
    }

}
