package org.example.com.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sourcename;  // 원본 파일명
    private String filename;    // 저장된 파일명
    private LocalDateTime localDateTime;

    @ManyToOne
    private FindRoom findRoom;
}
