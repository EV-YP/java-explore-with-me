package ru.practicum.user.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.user.model.User;

import java.util.Collection;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    User save(User newUser);

    Optional<User> findUserById(Integer id);

    Page<User> findByIdIn(Collection<Integer> ids, Pageable pageable);
}
