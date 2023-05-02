package net.javaservice.diplomaservice.event.service;

import lombok.RequiredArgsConstructor;
import net.javaservice.diplomaservice.event.entity.Event;
import net.javaservice.diplomaservice.event.entity.Image;
import net.javaservice.diplomaservice.event.entity.Tag;
import net.javaservice.diplomaservice.event.repository.EventRepository;
import net.javaservice.diplomaservice.event.repository.ImageRepository;
import net.javaservice.diplomaservice.event.repository.TagRepository;
import net.javaservice.diplomaservice.event.request.EventRequest;
import net.javaservice.diplomaservice.event.response.EventResponse;
import net.javaservice.diplomaservice.event.response.ImageResponse;
import net.javaservice.diplomaservice.event.response.TagResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final ImageRepository imageRepository;
    private final TagRepository tagRepository;

    public ArrayList<EventResponse> getEvents(Integer page, Integer size) {
        Pageable firstPageWithTwoElements = PageRequest.of(page, size);
        var events = eventRepository.findAll(firstPageWithTwoElements).toList();

        ArrayList<EventResponse> listEventResponse = new ArrayList<EventResponse>();

        for(int i = 0; i < events.size(); i++) {
            var event = events.get(i);

            listEventResponse.add(i, convertToEventResponse(event));
        }

        return listEventResponse;
    }

    public EventResponse getEvent(Integer id) {
        var event = eventRepository.findById(id);

        return convertToEventResponse(event.get());
    }


    public ArrayList<EventResponse> searchEventOnText(String title) {
        var events = eventRepository.findByEventOnText(title);
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
            var event = eventRepository.findByTags_Name(tags.get(i));

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
        eventResponse.setCountPeopleMax(event.getCountMax());
        eventResponse.setCountPeople(event.getCountPerson());
        eventResponse.setTags(tagResponses);
        eventResponse.setImages(imageResponses);
        return eventResponse;
    }

    public Boolean addEvent(EventRequest eventRequest) {
        Event event = new Event();

        event.setAvatar(eventRequest.getAvatar());
        event.setDescription(eventRequest.getDescription());
        event.setDatetime(eventRequest.getDatetime());
        event.setCountMax(eventRequest.getCountPeopleMax());
        event.setCountPerson(0);
        event.setTitle(eventRequest.getTitle());
        event.setLatitude(eventRequest.getLatitude());
        event.setLongitude(eventRequest.getLongitude());

        var newEvent = eventRepository.save(event);

        return true;
    }

    public Boolean updateEvent(Integer id, EventRequest eventRequest) {
        var event = eventRepository.findById(id).orElseThrow();
        var tags = tagRepository.findAll();

        ArrayList<Tag> needTags = new ArrayList<>();

        tags.forEach(tag -> {
            eventRequest.getTags().forEach(eventTag -> {
                if (tag.getName().equals(eventTag.getName())) {
                    needTags.add(tag);
                }
            });
        });

        event.setTags(needTags);
        event.setAvatar(eventRequest.getAvatar());
        event.setDescription(eventRequest.getDescription());
        event.setDatetime(eventRequest.getDatetime());
        event.setCountMax(eventRequest.getCountPeopleMax());
        event.setCountPerson(0);
        event.setTitle(eventRequest.getTitle());
        event.setLatitude(eventRequest.getLatitude());
        event.setLongitude(eventRequest.getLongitude());

        var newEvent = eventRepository.save(event);

        return true;
    }
}