package net.javaservice.diplomaservice.user.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.javaservice.diplomaservice.authorization.entity.Role;
import net.javaservice.diplomaservice.event.response.EventResponse;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private long id;
    private String firstname;
    private String lastname;
    private String middlename;
    private Integer course;
    private String department;
    private String avatar;
    private String email;
    private String password;
    private Role role;
    private List<EventResponse> events;
}