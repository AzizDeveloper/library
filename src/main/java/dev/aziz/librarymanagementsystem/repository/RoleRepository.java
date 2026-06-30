package dev.aziz.librarymanagementsystem.repository;

import dev.aziz.librarymanagementsystem.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String roleName);
    Role findByNameEqualsIgnoreCase(String roleName);

}
