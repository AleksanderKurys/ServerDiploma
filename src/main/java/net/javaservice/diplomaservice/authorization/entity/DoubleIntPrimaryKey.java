package net.javaservice.diplomaservice.authorization.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoubleIntPrimaryKey implements Serializable {

    private Integer event_id;

    private Long user_id;

}
