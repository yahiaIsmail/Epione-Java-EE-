package EPIONE.JAVAEE.presentation.mbeans.util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendMail {
    public static boolean mail(String to, String subject, String body) {
        final String username = "haddadmoezPI@gmail.com";
        final String password = "PI_mail_test";

        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("haddadmoezPI@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body,"utf-8", "html");

            Transport.send(message);

            System.out.println("Done");
            return true;
        } catch (MessagingException e) {
            System.out.println(e.toString());
            System.out.println(e.getMessage());
            return false;
        }
    }

}
