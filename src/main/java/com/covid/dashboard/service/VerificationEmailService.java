package com.covid.dashboard.service;

import com.covid.dashboard.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
public class VerificationEmailService {

    @Autowired
    private JavaMailSender javaMailSender;


    public void sendVerificationEmail(String url, User user,String redirectUrl){
        String subject = "Please verify your registration";
        String senderName = "Covid Tracker Team";
        String mailContent = "<p>Dear "+user.getFirstName()+" "+user.getLastName()+",</p>" ;
        url +="?code="+user.getVerificationCode()+"&&redirectUrl="+redirectUrl;
        mailContent += "<p>Please click the link below to verify your registration</p>";
        mailContent += "<h2><a href=\""+ url +"\">VERIFY</a></h2>";
        mailContent += "<p>Thank you</p><br>Covid Tracker Team";

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        try {
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setTo(user.getEmail());
            mimeMessageHelper.setFrom("no.reply.covidtrack@gmail.com",senderName);
            mimeMessageHelper.setText(mailContent,true);

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
