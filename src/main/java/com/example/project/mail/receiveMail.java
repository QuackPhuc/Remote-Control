package com.example.project.mail;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Date;

public class receiveMail {
    private String username;
    private String password;
    private Date date;
    private String from;
    private String subject;
    private String content;

    public receiveMail(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getContent() {
        return content;
    }

    public Date getDate() {
        return date;
    }

    public String getFrom() {
        return from;
    }

    public String getSubject() {
        return subject;
    }

    public void receiveMail() {

        Properties props = new Properties();
        props.put("mail.store.protocol", "imaps");
        props.put("mail.imaps.host", "imap.gmail.com");
        props.put("mail.imaps.port", "993");
        props.put("mail.imaps.starttls.enable", "true");
        props.put("mail.imaps.ssl.protocols", "TLSv1.2");

        try {
            Session session = Session.getDefaultInstance(props, null);
            Store store = session.getStore("imaps");
            store.connect("imap.gmail.com", username, password);
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_WRITE);
            Message[] messages = inbox.getMessages();
            Message latestMessage = messages[messages.length - 1];
            this.date = latestMessage.getSentDate();
            this.subject= latestMessage.getSubject();
            try {
                InternetAddress[] addresses = InternetAddress.parse(latestMessage.getFrom()[0].toString(), true);
                for (InternetAddress address : addresses) {
                    String email = address.getAddress();
                    this.from=email;

                }
            } catch (AddressException e) {
                e.printStackTrace();
            }
            this.content = getMessageContentAsString(latestMessage);
            inbox.close(false);
            store.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private String getMessageContentAsString(Message message) throws MessagingException, IOException {
        Object content = message.getContent();

        if (content instanceof Multipart) {
            Multipart multipart = (Multipart) content;
            StringBuilder textContent = new StringBuilder();

            for (int i = 0; i < multipart.getCount(); i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                textContent.append((String) bodyPart.getContent());
            }
            return textContent.toString().split("\n")[0];
        }
        return this.content.toString();
    }
}