package net.javaservice.diplomaservice.event.service;

import lombok.RequiredArgsConstructor;
import net.javaservice.diplomaservice.event.entity.Event;
import net.javaservice.diplomaservice.event.repository.EventRepository;
import net.javaservice.diplomaservice.event.response.EventResponse;
import net.javaservice.diplomaservice.event.response.ImageResponse;
import net.javaservice.diplomaservice.event.response.TagResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public ArrayList<EventResponse> searchEventOnTag(List<String> tags) {
        ArrayList<EventResponse> listEventResponse = new ArrayList<EventResponse>();

        for(int i = 0; i<tags.size(); i++) {
            var event = repository.findByTags_Name(tags.get(i));

            for(int j=0; j < event.size(); j++) {
                listEventResponse.add(j, convertToEventResponse(event.get(j)));
            }
        }

        Set<EventResponse> set = new HashSet<EventResponse>(listEventResponse);
        listEventResponse.clear();
        listEventResponse.addAll(set);

        return listEventResponse;
    }

    private EventResponse convertToEventResponse(Event event) {
        EventResponse eventResponse = new EventResponse();
        List<TagResponse> tagResponses = new ArrayList<TagResponse>();
        List<ImageResponse> imageResponses = new ArrayList<ImageResponse>();

        var listTag = event.getTags().stream().toList();

        for (int i = 0; i < listTag.size(); i++) {
            TagResponse tagResponse = new TagResponse();
            tagResponse.setId(listTag.get(i).getId());
            tagResponse.setName(listTag.get(i).getName());
            tagResponses.add(i, tagResponse);
        }

        for (int i = 0; i < event.getImages().size(); i++) {
            ImageResponse imageResponse = new ImageResponse();
            imageResponse.setId(event.getImages().get(i).getId());
            imageResponse.setImageData(event.getImages().get(i).getImageData());
            imageResponses.add(i, imageResponse);
        }

        eventResponse.setId(event.getId());
        eventResponse.setTitle(event.getTitle());
        eventResponse.setAvatar(event.getAvatar());
        eventResponse.setDescription(event.getDescription());
        eventResponse.setDatetime(event.getDatetime());
        eventResponse.setLatitude(event.getLatitude());
        eventResponse.setLongitude(event.getLongitude());
        eventResponse.setCount_person(event.getCountPerson());
        eventResponse.setTags(tagResponses);
        eventResponse.setImages(imageResponses);
        return eventResponse;
    }
}