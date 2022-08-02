package com.funsoft.cabinet.security.service;
 
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.funsoft.cabinet.security.model.AppUser;
import com.funsoft.cabinet.security.repository.RoleRepository;
import com.funsoft.cabinet.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


 
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
 
    @Autowired
    private UserRepository userRepository;
 
    @Autowired
    private RoleRepository appRoleDAO;
 
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        AppUser appUser = userRepository.findByUserName(userName);
 
        if (appUser == null)
            throw new UsernameNotFoundException("User " + userName + " was not found in the database");


        // [ROLE_USER, ROLE_ADMIN,..]
        List<String> roleNames = appUser.getRoles().stream()
                .map(role -> role.getAppRole().getRoleName())
                .collect(Collectors.toList());
 
        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
        if (roleNames != null) {
            for (String role : roleNames) {
                // ROLE_USER, ROLE_ADMIN,..
                GrantedAuthority authority = new SimpleGrantedAuthority(role);
                grantList.add(authority);
            }
        }
 
        UserDetails userDetails = (UserDetails) new User(
                appUser.getUserName(), // username
                appUser.getEncrytedPassword(), // crypted password
                grantList); // granted access
 
        return userDetails;
    }
 
}