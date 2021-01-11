package com.fastcode.example.domain.core.authorization.userpermission;

import java.io.Serializable;
import java.time.*;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserpermissionId implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long permissionId;
    private Long userId;

    public UserpermissionId(Long permissionId, Long userId) {
        this.permissionId = permissionId;
        this.userId = userId;
    }
}
