package com.funsoft.cabinet.security.repository;

import com.funsoft.cabinet.security.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AppUser,Long> {

    public AppUser findByUserName(String username); // Authentification
}
