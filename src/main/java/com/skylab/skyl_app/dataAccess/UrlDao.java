package com.skylab.skyl_app.dataAccess;

import com.skylab.skyl_app.entities.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlDao extends JpaRepository<Url, Integer> {
    Optional<Url> findByAlias(String alias);

}
