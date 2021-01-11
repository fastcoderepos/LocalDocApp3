package com.fastcode.example.domain.core.authorization.userpreference;

import com.fastcode.example.domain.core.abstractentity.AbstractEntity;
import com.fastcode.example.domain.core.authorization.user.UserEntity;
import java.time.*;
import javax.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "userpreference")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class UserpreferenceEntity extends AbstractEntity {

    @Basic
    @Column(name = "theme", nullable = false, length = 256)
    private String theme;

    @Basic
    @Column(name = "language", nullable = false, length = 256)
    private String language;

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "id")
    private UserEntity user;
}
