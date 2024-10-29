package com.skylab.skyl_app.business.concretes;

import com.skylab.skyl_app.business.abstracts.EmailActivationService;
import com.skylab.skyl_app.business.abstracts.UserService;
import com.skylab.skyl_app.core.exceptions.ActivationCodeAlreadyConfirmedException;
import com.skylab.skyl_app.core.exceptions.ActivationCodeIsntFoundException;
import com.skylab.skyl_app.core.exceptions.UserDoesntExistsException;
import com.skylab.skyl_app.core.utilities.mail.EmailService;
import com.skylab.skyl_app.core.utilities.mail.JavaMailSenderManager;
import com.skylab.skyl_app.dataAccess.EmailActivationDao;
import com.skylab.skyl_app.entities.EmailActivation;
import com.skylab.skyl_app.entities.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailActivationManager implements EmailActivationService {

    private final EmailService emailService;

    private final UserService userService;
    private final EmailActivationDao emailActivationDao;
    private final JavaMailSenderManager javaMailSenderManager;

    public EmailActivationManager(EmailService emailService, UserService userService, EmailActivationDao emailActivationDao, JavaMailSenderManager javaMailSenderManager) {
        this.emailService = emailService;
        this.userService = userService;
        this.emailActivationDao = emailActivationDao;
        this.javaMailSenderManager = javaMailSenderManager;
    }

    @Override
    public boolean sendActivationEmail(User user) {
        var emailActivation = new EmailActivation();

        String activationCode = generateActivationCode();

        emailActivation.setActivationCode(activationCode);
        emailActivation.setUser(user);
        emailActivation.setConfirmed(false);
        emailActivation.setExpirationDate(LocalDateTime.now().plusDays(1));
        emailActivationDao.save(emailActivation);

        String mailSubject = "SKYL.APP KAYIT KODU";
        String mailBody = "SKYL.APP KAYIT İŞLEMİNİ TAMAMLAMAK İÇİN BU LİNKE TIKLAYINIZ http://localhost:8080/emailActivations/"+activationCode+ " LİNK ATILDIĞI TARİHTEN İTİBAREN 1 GÜM GEÇERLİDİR!";

        emailService.send(user.getEmail(), mailSubject, mailBody);
        return true;
    }



    @Override
    public boolean activateUser(String activationCode) {
        var activationCodeResult = emailActivationDao.findByActivationCode(activationCode);

        if (activationCodeResult.isEmpty()){
            throw new ActivationCodeIsntFoundException("Activation code is not valid!");
        }

        if (activationCodeResult.get().isConfirmed()){
            throw new ActivationCodeAlreadyConfirmedException("The activation code that youve been sent is already confirmed!");
        }

        activationCodeResult.get().setConfirmed(true);

        emailActivationDao.save(activationCodeResult.get());

        userService.activateUserById(activationCodeResult.get().getUser().getId());

        return true;
    }



    private String generateActivationCode() {
        String activationCode = "";
        String characters = "0123456789";

        for (int i = 0; i < 6; i++) {
            int index = (int) (characters.length() * Math.random());
            activationCode += characters.charAt(index);
        }

        return activationCode;
    }

}
