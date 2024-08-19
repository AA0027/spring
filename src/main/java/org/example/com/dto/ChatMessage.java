package org.example.com.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.com.domain.Attachment;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessage {
    private String code;
    private String username;
    private String content;
    private String type;
    private List<Attachment> files;
    private String regdate;
}
