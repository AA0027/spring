package org.example.com.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
public class UserSubPK implements Serializable {
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "room_id")
    private Long roomId;

    public UserSubPK(Long userId, Long roomId) {
        this.userId = userId;
        this.roomId = roomId;
    }
}




