package net.javaservice.diplomaservice.event.repository;


import net.javaservice.diplomaservice.event.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface ImageRepository extends JpaRepository<Image, Integer> {
    ArrayList<Image> findByEventId(Integer eventId);
}