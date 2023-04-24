package net.javaservice.diplomaservice.event.service;

import lombok.RequiredArgsConstructor;
import net.javaservice.diplomaservice.event.repository.EventRepository;
import net.javaservice.diplomaservice.event.response.EventResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository repository;
    public ArrayList<EventResponse> getEvents(Integer page, Integer size) {
        Pageable firstPageWithTwoElements = PageRequest.of(page, size);
        var events = repository.findAll(firstPageWithTwoElements).toList();
        ArrayList<EventResponse> listEventResponse = new ArrayList<EventResponse>();
        EventResponse eventResponse = new EventResponse();

        for(int i = 0; i < events.size(); i++) {
            var event = events.get(0);
            Set<String> setTags = null;

            event.getTags().forEach(tag -> {
                setTags.add(tag.getName());
            });
            eventResponse.setTitle(event.getTitle());
            eventResponse.setAvatar(event.getAvatar());
            eventResponse.setDescription(event.getDescription());
            eventResponse.setDatetime(event.getDatetime());
            eventResponse.setLatitude(event.getLatitude());
            eventResponse.setTags(setTags);
            eventResponse.setLongitude(event.getLongitude());
            eventResponse.setCount_person(event.getCount_person());
            listEventResponse.add(i, eventResponse);
        }

        return listEventResponse;
    }
}