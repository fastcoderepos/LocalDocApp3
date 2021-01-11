package com.fastcode.example.security;

import com.fastcode.example.domain.core.authorization.user.IUserRepository;
import com.fastcode.example.domain.core.authorization.user.UserEntity;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    @Qualifier("userRepository")
    @NonNull
    private final IUserRepository usersRepository;

    @NonNull
    private final SecurityUtils securityUtils;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity applicationUser = usersRepository.findByUserName(username);

        if (applicationUser == null) {
            throw new UsernameNotFoundException(username);
        }

        List<String> permissions = securityUtils.getAllPermissionsFromUserAndRole(applicationUser);
        String[] groupsArray = new String[permissions.size()];
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(permissions.toArray(groupsArray));

        return new User(applicationUser.getUserName(), applicationUser.getPassword(), authorities); // User class implements UserDetails Interface
    }
}
