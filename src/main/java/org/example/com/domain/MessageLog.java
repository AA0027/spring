package org.example.com.domain;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
@Document(collection = "message_log")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageLog {
    @Id
    private String id;
    private String code;
    private Employee sender;
    private String content;
    private String regdate;
}
