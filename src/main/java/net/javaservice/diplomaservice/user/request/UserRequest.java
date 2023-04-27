package net.javaservice.diplomaservice.user.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private String firstname;
    private String lastname;
    private String middlename;
    private Integer course;
    private String department;
    private String avatar;
    private String email;
}
