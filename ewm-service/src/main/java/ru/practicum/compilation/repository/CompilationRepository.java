package ru.practicum.compilation.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.compilation.model.Compilation;

import java.util.Optional;

public interface CompilationRepository extends JpaRepository<Compilation, Integer> {

    Optional<Compilation> findCompilationById(Integer compId);

    Compilation save(Compilation compilation);

    Page<Compilation> findCompilationByPinned(Boolean pinned, Pageable pageable);
}
