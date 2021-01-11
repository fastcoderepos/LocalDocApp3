package com.fastcode.example.domain.core.authorization.userpermission;

import com.fastcode.example.domain.core.abstractentity.AbstractEntity;
import com.fastcode.example.domain.core.authorization.permission.PermissionEntity;
import com.fastcode.example.domain.core.authorization.user.UserEntity;
import java.time.*;
import javax.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "userpermission")
@IdClass(UserpermissionId.class)
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class UserpermissionEntity extends AbstractEntity {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "permission_id", nullable = false)
    private Long permissionId;

    @Basic
    @Column(name = "revoked", nullable = true)
    private Boolean revoked;

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "permission_id", insertable = false, updatable = false)
    private PermissionEntity permission;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private UserEntity user;
}
