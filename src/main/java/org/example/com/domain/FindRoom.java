package org.example.com.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(
                columnNames={"chatRoom_id", "employee_id"}
        )
})
public class FindRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    private Employee employee;


    @ManyToOne
    private ChatRoom chatRoom;
}
