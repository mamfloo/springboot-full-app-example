package com.library.demo.Service;

public interface MailService {

    public void sendEmail(String subject, String emailText, String... destinations);
}
