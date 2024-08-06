package org.example.com.repo;

import org.example.com.domain.MessageLog;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface MessageLogRepository extends MongoRepository<MessageLog, Long> {
    Optional<List<MessageLog>> findMessageLogsByCodeOrderByRegdateDesc(String code);
}
