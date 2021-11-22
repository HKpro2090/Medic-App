package com.example.medic_app;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.StrictMode;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class GMailSender extends AsyncTask<String,String,String> {




    @Override
    protected String doInBackground(String... params) {

            String from = params[0];
            String password=params[1];
            String to = params[2] ;
            String sub = params[3];
            String msg = params[4];

            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class",
                    "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");

            Session session = Session.getDefaultInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(from, password);
                        }
                    });

            try {
                MimeMessage message = new MimeMessage(session);
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
                message.setSubject(sub);
                message.setText(msg);
                //send message
                Transport.send(message);


            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }

    return null;
    }
}








