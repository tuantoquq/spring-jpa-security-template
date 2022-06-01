package com.openway.springtemplateapollo.service;

import com.openway.springtemplateapollo.constants.RoleEnum;
import com.openway.springtemplateapollo.entity.Role;
import com.openway.springtemplateapollo.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public void addRole(String role) {
        Role roleInit = new Role();
        roleInit.setRoleName(RoleEnum.valueOf(role.toUpperCase()));
        roleInit.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        roleRepository.save(roleInit);
    }
}
