package br.com.webpublico.security;

import br.com.webpublico.domain.UsuarioNotaPremiadaDTO;
import br.com.webpublico.service.UsuarioNotaPremiadaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(UserDetailsService.class);

    @Autowired
    private UsuarioNotaPremiadaService usuarioNotaPremiadaService;

    @Override
    public UserDetails loadUserByUsername(final String login) {
        log.debug("Authenticating {}", login.toLowerCase());

        Optional<UsuarioNotaPremiadaDTO> userFromDatabase = Optional.of(usuarioNotaPremiadaService.findByLogin(login.toLowerCase()));

        return userFromDatabase.map(user -> {
            if (!user.getAtivo()) {
                throw new UserNotActivatedException("User " + login.toLowerCase() + " was not activated");
            }
            List<GrantedAuthority> grantedAuthorities = user.getRoles().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

            return new org.springframework.security.core.userdetails.User(login.toLowerCase(),
                user.getPassword(),
                grantedAuthorities);

        }).orElseThrow(() -> new UsernameNotFoundException("User " + login.toLowerCase() + " was not found in the " +
            "database"));
    }
}
