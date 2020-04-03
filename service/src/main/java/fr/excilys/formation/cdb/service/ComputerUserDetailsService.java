package fr.excilys.formation.cdb.service;

import fr.excilys.formation.cdb.dao.UserDAO;
import fr.excilys.formation.cdb.model.UserRole;
import fr.excilys.formation.cdb.model.Users;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ComputerUserDetailsService implements UserDetailsService {

    private final UserDAO userDao;

    public ComputerUserDetailsService(UserDAO userDao) {
        this.userDao = userDao;
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        List<GrantedAuthority> authorities;
        Users user = userDao.findByUserName(username);

        if (user != null) {
            authorities = buildUserAuthority(user.getUserRole());
        } else {
            throw new UsernameNotFoundException("not found username: " + username);
        }

        return buildUserForAuthentication(user, authorities);
    }

    private User buildUserForAuthentication(Users user, List<GrantedAuthority> authorities) {
        return new User(user.getUsername(), user.getPassword(), user.isEnabled(),
                true, true, true,
                authorities);
    }

    private List<GrantedAuthority> buildUserAuthority(Set<UserRole> userRoles) {
        return userRoles.stream().map(ComputerUserDetailsService::apply).distinct()
                .collect(Collectors.toList());
    }

    private static SimpleGrantedAuthority apply(UserRole userRole) {
        return new SimpleGrantedAuthority(userRole.getRole());
    }

    public boolean addNewUser(String username, String password, String role) {
        boolean isAdded = false;
        Users user = findByUserName(username);
        if (user == null) {
            userDao.addNewUser(getUser(username, password, role));
            isAdded = true;
        }

        return isAdded;
    }

    public Users findByUserName(String username) {
        return userDao.findByUserName(username);
    }

    public Users getUser(String username, String password, String role) {
        String passwordEncoded = PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(password);
        Users user = new Users(username, passwordEncoded, true);
        UserRole userRole = new UserRole(user, role);
        Set<UserRole> userRoleSet = new HashSet<>();
        userRoleSet.add(userRole);
        user.setUserRole(userRoleSet);

        return user;
    }
}