package com.fastcode.example.domain.core.authorization.tokenverification;

import com.fastcode.example.domain.core.abstractentity.AbstractEntity;
import com.fastcode.example.domain.core.authorization.user.UserEntity;
import java.time.*;
import java.util.Date;
import javax.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tokenverification")
@IdClass(TokenverificationId.class)
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class TokenverificationEntity extends AbstractEntity {

    @Basic
    @Column(name = "expiration_time", nullable = true)
    private Date expirationTime;

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "token_type", nullable = false, length = 256)
    private String tokenType;

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Basic
    @Column(name = "token", nullable = true, length = 512)
    private String token;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private UserEntity user;
}
