package net.javaservice.diplomaservice.event.repository;

import net.javaservice.diplomaservice.authorization.entity.DoubleIntPrimaryKey;
import net.javaservice.diplomaservice.authorization.entity.UserEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEventRepository extends JpaRepository<UserEvent, DoubleIntPrimaryKey> {
}
