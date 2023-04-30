package edu.tcu.cs.superfrogscheduler.email;

import edu.tcu.cs.superfrogscheduler.user.User;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(String to, String from, String subject, String text){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(text);
        javaMailSender.send(simpleMailMessage);
    }

    public void emailAllUsers(List<User> users, Long requestId){
        for(int i = 0; i<users.size(); i++){
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(users.get(i).getEmail());
            simpleMailMessage.setFrom("superfrogschedulercite30363@gmail.com");
            simpleMailMessage.setSubject("New Open Approved Request");
            simpleMailMessage.setText("Dear Superfrog,\n" + "A new appearance request (ID: " + requestId.toString() + ") has been approved and is open for sign up.");
            javaMailSender.send(simpleMailMessage);
        }
    }
}
