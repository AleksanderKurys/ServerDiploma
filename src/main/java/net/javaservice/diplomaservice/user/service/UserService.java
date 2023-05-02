package net.javaservice.diplomaservice.user.service;

import lombok.RequiredArgsConstructor;
import net.javaservice.diplomaservice.authorization.entity.User;
import net.javaservice.diplomaservice.authorization.repository.UserRepository;
import net.javaservice.diplomaservice.event.entity.Event;
import net.javaservice.diplomaservice.event.response.EventResponse;
import net.javaservice.diplomaservice.event.response.ImageResponse;
import net.javaservice.diplomaservice.event.response.TagResponse;

import net.javaservice.diplomaservice.user.request.UserRequest;
import net.javaservice.diplomaservice.user.response.UserResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public UserResponse getUser(Integer id) {
        var user = repository.findById(id);

        return convertToUserResponse(user.get());
    }

    public boolean updateUser(UserRequest userRequest) {
        var user = repository.findByEmail(userRequest.getEmail());

        user.get().setDepartment(userRequest.getDepartment());
        user.get().setAvatar(userRequest.getAvatar());
        user.get().setCourse(userRequest.getCourse());
        user.get().setEmail(userRequest.getEmail());
        user.get().setFirstname(userRequest.getFirstname());
        user.get().setLastname(userRequest.getLastname());
        user.get().setMiddlename(userRequest.getMiddlename());

        var request = repository.save(user.get());

        if(request.isEnabled()) {
            return true;
        }
        return false;
    }
    public UserResponse getUserToEmail(String email) {
        var user = repository.findByEmail(email);


        return convertToUserResponse(user.get());
    }

    public ArrayList<EventResponse> getEventsAttended(String email) {
        var user = repository.findByEmail(email);
        var userEvents = user.get().getUserEvents();
        ArrayList<EventResponse> listEventResponse = new ArrayList<EventResponse>();

        userEvents.forEach(userEvent -> {
            var event = userEvent.getEvent();
            listEventResponse.add(convertToEventResponse(event));
        });

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
        eventResponse.setTags(tagResponses);
        eventResponse.setImages(imageResponses);
        return eventResponse;
    }
    private UserResponse convertToUserResponse(User user) {
        UserResponse userResponse = new UserResponse();

        userResponse.setId(user.getId());
        userResponse.setEmail(user.getEmail());
        userResponse.setAvatar(user.getAvatar());
        userResponse.setCourse(user.getCourse());
        userResponse.setDepartment(user.getDepartment());
        userResponse.setFirstname(user.getFirstname());
        userResponse.setLastname(user.getLastname());
        userResponse.setMiddlename(user.getMiddlename());
        userResponse.setRole(user.getRole());
        userResponse.setPassword(user.getPassword());

        return userResponse;
    }
}