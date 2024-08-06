package org.example.com.domain;

import jakarta.persistence.*;
import lombok.*;
import org.example.com.type.Dept;
import org.example.com.type.Position;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Position position;

    @Enumerated(EnumType.STRING)
    private Dept dept;

    private String username;
    private String password;
    private String code;
    private String role;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    @ToString.Exclude
    @Builder.Default
    private List<FindRoom> findRooms = new ArrayList<>();


    public static Employee employee(String name, String username, String password){
        return Employee.builder()
                .name(name)
                .username(username)
                .password(password)
                .build();
    }
}
