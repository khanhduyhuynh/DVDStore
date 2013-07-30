/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.capstone.EJB;

import java.util.Date;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author duy
 */

@Stateless
public class EmailEJB 
{
    @Resource(name = "mail/mailDVDStore")
    private Session mailSession;


    public void sendMessage(String email, String subject, String bodyMessage) throws MessagingException 
    {        
        Message message = new MimeMessage(mailSession);
        try 
        {
            message.setSubject(subject);
            message.setRecipient(RecipientType.TO, new InternetAddress(email)); 
            // this is if you want message body as text
            //            message.setText(bodyMessage); 
            
            // use this is if you want to send html based message
            message.setContent(bodyMessage, "text/html; charset=utf-8");

            // This is not mandatory, however, it is a good
            // practice to indicate the software which
            // constructed the message.
            message.setHeader("X-Mailer", "MINIpay mailer www.minipay.eu");

            // Adjust the date of sending the message
            Date timeStamp = new Date();
            message.setSentDate(timeStamp);

            Transport.send(message);
        } 
        catch (MessagingException ex) 
        {
            throw ex;
        }
    }
}
