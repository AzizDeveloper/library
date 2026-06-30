package dev.aziz.librarymanagementsystem.entity;

import lombok.AllArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@AllArgsConstructor
public class UserDetailsImpl implements UserDetails  {

    private final Reader reader;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return reader.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .toList();
    }

    @Override
    public @Nullable String getPassword() {
        return reader.getPassword();
    }

    @Override
    public String getUsername() {
        return reader.getEmail();
    }

    public Reader getReader() {
        return reader;
    }
}
