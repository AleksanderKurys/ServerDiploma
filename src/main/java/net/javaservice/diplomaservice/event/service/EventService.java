package net.javaservice.diplomaservice.event.service;

import lombok.RequiredArgsConstructor;
import net.javaservice.diplomaservice.event.entity.Event;
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

        for(int i = 0; i < events.size(); i++) {
            var event = events.get(i);

            listEventResponse.add(i, convertToEventResponse(event));
        }

        return listEventResponse;
    }

    public EventResponse getEvent(Integer id) {
        var event = repository.findById(id);

        return convertToEventResponse(event.get());
    }


    public ArrayList<EventResponse> searchEventOnText(String title) {
        var events = repository.findByEventOnText(title);
        ArrayList<EventResponse> listEventResponse = new ArrayList<EventResponse>();

        for(int i=0; i<events.size(); i++) {
            var event = events.get(i);
            listEventResponse.add(i, convertToEventResponse(event));
        }

        return listEventResponse;
    }

    private EventResponse convertToEventResponse(Event event) {
        EventResponse eventResponse = new EventResponse();
        eventResponse.setId(event.getId());
        eventResponse.setTitle(event.getTitle());
        eventResponse.setAvatar(event.getAvatar());
        eventResponse.setDescription(event.getDescription());
        eventResponse.setDatetime(event.getDatetime());
        eventResponse.setLatitude(event.getLatitude());
        eventResponse.setTags(event.getTags());
        eventResponse.setImages(event.getImages());
        eventResponse.setLongitude(event.getLongitude());
        eventResponse.setCount_person(event.getCountPerson());
        return eventResponse;
    }
}