package net.javaservice.diplomaservice.event.repository;

import net.javaservice.diplomaservice.event.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;

public interface EventRepository extends JpaRepository<Event, Integer> {
    @Query("SELECT e FROM Event e WHERE e.title LIKE %?1%")
    ArrayList<Event> findByEventOnText(String title);
}
