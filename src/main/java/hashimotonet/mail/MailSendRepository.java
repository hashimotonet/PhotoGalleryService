package hashimotonet.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import hashimotonet.mail.template.MailTemplate;
import hashimotonet.mail.template.MailTemplateImpl;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MailSendRepository {

    @Value("${sender.mail-address}")
    private String fromEmail;

    @Autowired
    private MailContentFactory mailContentFactory;
    
    @Autowired
    private JavaMailSender emailSender;
    
    private static final String SUBJECT = "【PhotoGallery】登録確認メール";
    
    /**
     * 登録確認メールを送信します。
     * @param title タイトル
     * @param body 内容
     * @param toEmailAddress 送り先アドレス
     */
    @Async
    public void sendRegistMail(String url, String toEmailAddress) {
    	
        // メール内容作成
        String content = mailContentFactory.create((MailTemplate) new MailTemplateImpl(url));
        
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper;
        
        // メール設定
        try {
            helper = new MimeMessageHelper(message, true /* multipart */, "UTF-8");
            helper.setFrom(fromEmail);
            helper.setTo(toEmailAddress);
            helper.setSubject(SUBJECT);
            helper.setText(content, true /* isHtml */); // HTMLコンテンツを設定し、isHtmlをtrueに設定する
            emailSender.send(message);
        } catch (MessagingException e) {
            // エラーハンドリング
            e.printStackTrace();
        }      
        
        // メール送信
        //mailSender.send(mailMessage);
    }
}
