package org.example.com.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "findRoom_id")
    private List<Attachment> files;
}
