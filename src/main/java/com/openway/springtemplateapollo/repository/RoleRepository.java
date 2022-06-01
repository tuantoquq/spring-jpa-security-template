package com.openway.springtemplateapollo.repository;

import com.openway.springtemplateapollo.constants.RoleEnum;
import com.openway.springtemplateapollo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    public Optional<Role> findRoleByRoleName(RoleEnum role);
}
