package com.fastcode.example.domain.core.actor;

import com.fastcode.example.domain.core.abstractentity.AbstractEntity;
import java.time.*;
import javax.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ACTOR")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class ActorEntity extends AbstractEntity {

    @Basic
    @Column(name = "FIRST_NAME", nullable = false, length = 45)
    private String firstName;

    @Basic
    @Column(name = "LAST_NAME", nullable = false, length = 45)
    private String lastName;

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "ACTOR_ID", nullable = false)
    private Integer actorId;

    @Basic
    @Column(name = "LAST_UPDATE", nullable = false)
    private LocalDateTime lastUpdate;
}
