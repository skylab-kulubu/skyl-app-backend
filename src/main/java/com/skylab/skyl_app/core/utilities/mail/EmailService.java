package com.skylab.skyl_app.core.utilities.mail;

public interface EmailService {

    boolean send(String to, String subject, String body);

}
