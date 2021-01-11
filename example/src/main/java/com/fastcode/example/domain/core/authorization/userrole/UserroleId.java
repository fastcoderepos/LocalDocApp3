package com.fastcode.example.domain.core.authorization.userrole;

import java.io.Serializable;
import java.time.*;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserroleId implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long roleId;
    private Long userId;

    public UserroleId(Long roleId, Long userId) {
        this.roleId = roleId;
        this.userId = userId;
    }
}
