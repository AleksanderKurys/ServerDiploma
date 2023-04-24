package net.javaservice.diplomaservice.event.repository;

import net.javaservice.diplomaservice.event.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Integer> {

}
