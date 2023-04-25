package net.javaservice.diplomaservice.event.service;

import lombok.RequiredArgsConstructor;
import net.javaservice.diplomaservice.event.repository.EventRepository;
import net.javaservice.diplomaservice.event.response.EventResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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

            eventResponse.setTitle(event.getTitle());
            eventResponse.setAvatar(event.getAvatar());
            eventResponse.setDescription(event.getDescription());
            eventResponse.setDatetime(event.getDatetime());
            eventResponse.setLatitude(event.getLatitude());
            eventResponse.setTags(event.getTags());
            eventResponse.setLongitude(event.getLongitude());
            eventResponse.setCount_person(event.getCount_person());
            listEventResponse.add(i, eventResponse);
        }

        return listEventResponse;
    }

    public EventResponse getEvent(Integer id) {
        var event = repository.findById(id);
        EventResponse eventResponse = new EventResponse();

        eventResponse.setTitle(event.get().getTitle());
        eventResponse.setAvatar(event.get().getAvatar());
        eventResponse.setDescription(event.get().getDescription());
        eventResponse.setDatetime(event.get().getDatetime());
        eventResponse.setLatitude(event.get().getLatitude());
        eventResponse.setTags(event.get().getTags());
        eventResponse.setLongitude(event.get().getLongitude());
        eventResponse.setCount_person(event.get().getCount_person());

        return eventResponse;
    }
}