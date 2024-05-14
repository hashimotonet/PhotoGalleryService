package hashimotonet.mail;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import hashimotonet.mail.template.MailTemplate;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailSenderService implements MailTemplate {

	@Autowired
    private MailSender mailSender;

    public void sendMail(String url, String toAddress) {
        var mailInfo = new SimpleMailMessage();
        mailInfo.setSubject("Javaの実装練習です");
        mailInfo.setText("お元気ですかテストです");
        mailInfo.setTo(FROM_ADDRESS);

        mailSender.send(mailInfo);
    }

	public MailSenderService(MailSender mailsender) {
		this.mailSender = mailsender;
	}
	
	/**
	 * 
	 * 
	 * @param url PhotoGalleryサービス登録URL
	 * @return
	 */
	private String createMailBody(String url) {
		return url;
	}

	@Override
	public String getTemplatePath() {
		return null;
	}

	@Override
	public Map<String, Object> getVariables() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
}
