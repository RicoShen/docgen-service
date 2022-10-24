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
            ClassPathResource file = new ClassPathResource("template/ca/construction_template.docx");
            XWPFTemplate template = XWPFTemplate.compile(file.getInputStream());
            // 段落内容替换，表格内容替换、页脚替换
            Map datas = new HashMap();
            datas.put("Loan_ID", "20220915KL"); // footer
            datas.put("Property_Address", "37 Country Club Drive, Hayward, CA 94542"); // footer
            datas.put("CLOSING_DOCUMENT_DATE", "September 17, 2022");
            datas.put("TITLE_COMPANY_NAME", "North American Title Company");
            datas.put("TITLE_COMPANY_ADDRESS", "689 Portola Drive");
            datas.put("TITLE_COMPANY_CITY_STATE_ZIPCODE", "San Francisco, CA 94127");
            datas.put("SIGNING_USER_NAME", "Connie Choy");
            datas.put("SIGNING_USER_EMAIL", "cmchoy@nat.com");
            datas.put("TITLE_ORDER_NO", "56606-21-04011");
            datas.put("TITLE_EFFECTIVE_DATE", "July 12, 2021");
            datas.put("PROPERTY_ADDRESS", "37 Country Club Drive, Hayward, CA 94542");
            datas.put("VESTING_CLAUSE_PRIVIEW", "KUN LIU LLC, a California limited liability company");
            datas.put("SIGNING_FIRST_NAME", "Connie");

            template.render(datas);

            XWPFDocument doc = template.getXWPFDocument();
            OutputStream out = new FileOutputStream("build/template_replace.docx");
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
