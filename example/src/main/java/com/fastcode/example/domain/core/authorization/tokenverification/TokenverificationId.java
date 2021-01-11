package com.fastcode.example.domain.core.authorization.tokenverification;

import java.io.Serializable;
import java.time.*;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TokenverificationId implements Serializable {

    private static final long serialVersionUID = 1L;

    private String tokenType;
    private Long userId;

    public TokenverificationId(String tokenType, Long userId) {
        this.tokenType = tokenType;
        this.userId = userId;
    }
}
