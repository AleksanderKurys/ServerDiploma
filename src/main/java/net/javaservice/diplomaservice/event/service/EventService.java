package net.javaservice.diplomaservice.event.service;

import lombok.RequiredArgsConstructor;
import net.javaservice.diplomaservice.authorization.entity.DoubleIntPrimaryKey;
import net.javaservice.diplomaservice.authorization.entity.UserEvent;
import net.javaservice.diplomaservice.authorization.repository.UserRepository;
import net.javaservice.diplomaservice.event.entity.Event;
import net.javaservice.diplomaservice.event.entity.Image;
import net.javaservice.diplomaservice.event.entity.Tag;
import net.javaservice.diplomaservice.event.repository.EventRepository;
import net.javaservice.diplomaservice.event.repository.ImageRepository;
import net.javaservice.diplomaservice.event.repository.TagRepository;
import net.javaservice.diplomaservice.event.repository.UserEventRepository;
import net.javaservice.diplomaservice.event.request.EventRequest;
import net.javaservice.diplomaservice.event.response.EventResponse;
import net.javaservice.diplomaservice.event.response.ImageResponse;
import net.javaservice.diplomaservice.event.response.TagResponse;
import net.javaservice.diplomaservice.event.util.ImageUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.*;

import static java.time.LocalDateTime.*;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final TagRepository tagRepository;
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;

    private final UserEventRepository userEventRepository;

    public ArrayList<EventResponse> getEvents(Integer page, Integer size) {
        Pageable firstPageWithTwoElements = PageRequest.of(page, size);
        LocalDate localDate = LocalDate.of(2025, 1, 1);

        var events1 = eventRepository.findAll();
        Date date = new Date(System.currentTimeMillis());
        ArrayList<EventResponse> listEventResponse = new ArrayList<EventResponse>();

        for(int i=0;i<events1.size();i++) {
             if (events1.get(i).getStartDateTime().after(date)) {
                 var event = events1.get(i);

                 listEventResponse.add(convertToEventResponse(event));
             }
        }

//        var events = eventRepository.findWhereBetween(
//                java.sql.Date.from(now().atZone(ZoneId.systemDefault()).toInstant()),
//                java.sql.Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
//
//
//
//        for(int i = 0; i < events.size(); i++) {
//            var event = events.get(i);
//
//            listEventResponse.add(i, convertToEventResponse(event));
//        }

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
        ArrayList<Event> listEventResponse = new ArrayList<Event>();

        LocalDate localDate = LocalDate.of(2025, 1, 1);

        var events1 = eventRepository.findAll();
        Date date = new Date(System.currentTimeMillis());

        for(int i=0;i<events1.size();i++) {
            if (events1.get(i).getStartDateTime().after(date)) {
                var event = events1.get(i);
                listEventResponse.add(event);
            }
        }
        ArrayList<EventResponse> listEventResponse1 = new ArrayList<EventResponse>();

        for(int i=0; i<listEventResponse.size(); i++) {
            for(int j=0;j<listEventResponse.get(i).getTags().size();j++) {
                for(int z=0;z<tags.size();z++) {
                    if (listEventResponse.get(i).getTags().get(j).getName().equals(tags.get(z))) {
                        listEventResponse1.add(convertToEventResponse(listEventResponse.get(i)));
                    }
                }
            }
        }

//        LocalDate localDate = LocalDate.of(2024, 1, 1);
//        var events = eventRepository.findWhereBetween(
//                java.sql.Date.from(now().atZone(ZoneId.systemDefault()).toInstant()),
//                java.sql.Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
//
//        for(int i = 0; i<events.size(); i++) {
//            for(int j = 0; j<events.get(i).getTags().size(); j++) {
//                for(int z=0; z<tags.size(); z++){
//                    if (events.get(i).getTags().get(j).getName().equals(tags.get(z))) {
//                        listEventResponse.add(convertToEventResponse(events.get(i)));
//                    }
//                }
//            }
//        }

//        for(int i = 0; i<tags.size(); i++) {
//            var event = eventRepository.findByTags_Name(tags.get(i));
//
//            for(int j=0; j < event.size(); j++) {
//                listEventResponse.add(j, convertToEventResponse(event.get(j)));
//            }
//        }
//
//        Set<EventResponse> set = new HashSet<EventResponse>(listEventResponse);
//        listEventResponse.clear();
//        listEventResponse.addAll(set);

        return listEventResponse1;
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
            imageResponse.setImageData(ImageUtils.decompressImage(event.getImages().get(i).getImageData()));
            imageResponses.add(i, imageResponse);
        }

        eventResponse.setId(event.getId());
        eventResponse.setTitle(event.getTitle());
        eventResponse.setAvatar(ImageUtils.decompressImage(event.getAvatar()));
        eventResponse.setDescription(event.getDescription());

        eventResponse.setStartDateTime(ZonedDateTime.ofInstant(event.getStartDateTime().toInstant(), ZoneId.systemDefault()));
        eventResponse.setEndDateTime(ZonedDateTime.ofInstant(event.getEndDateTime().toInstant(), ZoneId.systemDefault()));

        eventResponse.setLatitude(event.getLatitude());
        eventResponse.setLongitude(event.getLongitude());
        eventResponse.setCountPeopleMax(event.getCountMax());
        eventResponse.setCountPeople(event.getCountPerson());
        eventResponse.setTags(tagResponses);
        eventResponse.setImages(imageResponses);
        return eventResponse;
    }

    public Integer addEvent(EventRequest eventRequest) {
        Event event = new Event();
        event.setAvatar(ImageUtils.compressImage(eventRequest.getAvatar()));
        event.setDescription(eventRequest.getDescription());

        event.setStartDateTime(Date.from(eventRequest.getStartDateTime().atZone(ZoneId.systemDefault()).toInstant()));
        event.setEndDateTime(Date.from(eventRequest.getEndDateTime().atZone(ZoneId.systemDefault()).toInstant()));

        event.setCountMax(eventRequest.getCountPeopleMax());
        event.setCountPerson(0);
        event.setTitle(eventRequest.getTitle());
        event.setLatitude(eventRequest.getLatitude());
        event.setLongitude(eventRequest.getLongitude());

        var newEvent = eventRepository.save(event);
        var eventId = eventRepository.findByEventOnText(newEvent.getTitle());

        return eventId.get(0).getId();
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

        ArrayList<Image> needImage = new ArrayList<>();

        var images = eventRequest.getImages();
        images.forEach(imageRequest -> {
            Image image = new Image();
            image.setImageData(ImageUtils.compressImage(imageRequest.getImageData()));
            image.setEvent(event);
            needImage.add(image);
        });
        imageRepository.saveAll(needImage);

        event.setTags(needTags);
        event.setAvatar(ImageUtils.compressImage(eventRequest.getAvatar()));
        event.setDescription(eventRequest.getDescription());
        event.setStartDateTime(Date.from(eventRequest.getStartDateTime().toInstant(ZoneOffset.UTC)));
        event.setEndDateTime(Date.from(eventRequest.getEndDateTime().toInstant(ZoneOffset.UTC)));
        event.setCountMax(eventRequest.getCountPeopleMax());
        event.setTitle(eventRequest.getTitle());
        event.setLatitude(eventRequest.getLatitude());
        event.setLongitude(eventRequest.getLongitude());

        var newEvent = eventRepository.save(event);

        return true;
    }

    public List<EventResponse> getCoordinate() {
        LocalDate localDate = LocalDate.of(2025, 1, 1);

        var events1 = eventRepository.findAll();
        Date date = new Date(System.currentTimeMillis());
        ArrayList<EventResponse> listEventResponse = new ArrayList<EventResponse>();

        for(int i=0;i<events1.size();i++) {
            if (events1.get(i).getStartDateTime().after(date)) {
                var event = events1.get(i);

                listEventResponse.add(convertToEventResponse(event));
            }
        }

        return listEventResponse;
    }

    public Boolean signUpEvent(Integer id, String email) {
        var userEvent = new UserEvent();
        var user = userRepository.findByEmail(email).orElseThrow();
        var event = eventRepository.findById(id).orElseThrow();
        Boolean state = false;

        userEvent.setDoubleIntPrimaryKey(DoubleIntPrimaryKey.builder()
                .event_id(event.getId())
                .user_id(user.getId()).build());

        event.setCountPerson(event.getCountPerson()+1);

        userEvent.setUser(user);
        userEvent.setEvent(event);
        userEvent.setIsRegistered(true);
        userEvent.setIsVisited(false);

        userEventRepository.save(userEvent);

        return true;
    }

    public Boolean unsubscribeEvent(Integer eventId, String email) {
        var user = userRepository.findByEmail(email).orElseThrow();
        var event = eventRepository.findById(eventId).orElseThrow();

        for (int i=0; i<event.getUserEvent().size(); i++) {
            if (eventId == event.getUserEvent().get(i).getEvent().getId() && event.getUserEvent().get(i).getUser().getId() == user.getId()) {
                event.getUserEvent().get(i).setIsRegistered(!event.getUserEvent().get(i).getIsRegistered());
                event.getUserEvent().get(i).setIsVisited(false);
                event.setCountPerson(event.getCountPerson()-1);
                eventRepository.save(event);
                break;
            }
        }

        return false;
    }
}