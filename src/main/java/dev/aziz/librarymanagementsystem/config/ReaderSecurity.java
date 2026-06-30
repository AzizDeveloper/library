package dev.aziz.librarymanagementsystem.config;

import dev.aziz.librarymanagementsystem.entity.Reader;
import dev.aziz.librarymanagementsystem.entity.UserDetailsImpl;
import dev.aziz.librarymanagementsystem.repository.ReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ReaderSecurity {

    private final ReaderRepository repository;

    public boolean isOwner(Long id, Authentication authentication) {
//        String email = authentication.getName();
//        Reader reader = repository.findById(id).orElseThrow();

        Reader currentReader =
                ((UserDetailsImpl) Objects
                        .requireNonNull(authentication.getPrincipal()))
                        .getReader();

        return id.equals(currentReader.getId());
    }

}