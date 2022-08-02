package com.funsoft.cabinet.security.repository;

import com.funsoft.cabinet.security.model.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<AppRole,Long> {

    public AppRole findByRoleName(String rolename); // subscribe
}
