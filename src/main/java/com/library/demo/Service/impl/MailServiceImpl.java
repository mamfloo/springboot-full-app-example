package com.library.demo.Service.impl;

import com.library.demo.Service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender emailSender;

    public void sendEmail(String subject, String emailText, String... destinations){
        System.out.println("Sending mail to " + destinations[0] + " \n " + emailText  );
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("jadon.kiehn@ethereal.email");
        message.setTo(destinations);
        message.setSubject(subject);
        message.setText(emailText);
        emailSender.send(message);
    }

}
