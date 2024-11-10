package com.skylab.skyl_app.dataAccess;

import com.skylab.skyl_app.entities.Url;
import com.skylab.skyl_app.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UrlDao extends JpaRepository<Url, Integer> {
    Optional<Url> findByAlias(String alias);

    List<Url> findAllByCreatedBy(User loggedInUser);
}
