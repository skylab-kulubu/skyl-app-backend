package com.skylab.skyl_app.dataAccess;

import com.skylab.skyl_app.entities.EmailActivation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailActivationDao extends JpaRepository<EmailActivation, Integer> {
    Optional<EmailActivation> findByUserEmail(String email);
    Optional<EmailActivation> findByUserEmailAndActivationCode(String email, String activationCode);
    Optional<EmailActivation> findByActivationCode(String activationCode);
}
