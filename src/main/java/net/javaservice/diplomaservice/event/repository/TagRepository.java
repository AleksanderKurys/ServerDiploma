package net.javaservice.diplomaservice.event.repository;

import net.javaservice.diplomaservice.event.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Integer> { }