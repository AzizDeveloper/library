package dev.aziz.librarymanagementsystem.service;

import dev.aziz.librarymanagementsystem.entity.Reader;
import dev.aziz.librarymanagementsystem.entity.UserDetailsImpl;
import dev.aziz.librarymanagementsystem.repository.ReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final ReaderRepository readerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {

        Reader reader = readerRepository.findByEmailEqualsIgnoreCase(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(email));

        return new UserDetailsImpl(reader);
    }


}