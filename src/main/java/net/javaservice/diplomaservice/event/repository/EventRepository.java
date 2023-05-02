package net.javaservice.diplomaservice.event.repository;

import net.javaservice.diplomaservice.event.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {
    @Query("SELECT e FROM Event e WHERE e.title LIKE %?1%")
    ArrayList<Event> findByEventOnText(String title);

    List<Event> findByTags_Name(String name);

    @Query("Select e From Event e join fetch e.userEvent ue join fetch ue.user WHERE e.datetime between :startDate and :endDate")
    List<Event> findWhereBetween(Date startDate, Date endDate);
}
