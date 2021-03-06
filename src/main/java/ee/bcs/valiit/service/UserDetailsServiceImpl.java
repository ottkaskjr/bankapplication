package ee.bcs.valiit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private PasswordEncoder passwordEncoder; // reaalsuses peaks siin olema repository, näide on hardcoded

    @Override
    public UserDetails loadUserByUsername(String username){
        return User.withUsername("test").password(passwordEncoder.encode("test"))
                .roles("USER").build();
    }
}
