package com.example.unique.wear.auth.services;

import com.example.unique.wear.auth.model.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String from;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(User user) {
        String subject = "Verify your email";
        String senderName = "Unique Wear Account Service";
        String mailContent = "Hello " + user.getFirstName() + ",\n";
        mailContent += "Your verification code is: " + user.getVerificationCode() + "\n";
        mailContent += "Please enter this code to verify your email.";
        mailContent += "\n";
        mailContent += senderName;

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo(user.getEmail());
            message.setText(mailContent);
            message.setSubject(subject);
            mailSender.send(message);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


}
