package net.javaservice.diplomaservice.authorization.repository;

import net.javaservice.diplomaservice.authorization.entity.User;
import net.javaservice.diplomaservice.event.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository <User, Integer> {
    Optional<User> findByEmail(String email);
}
