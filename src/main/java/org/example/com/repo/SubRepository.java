package org.example.com.repo;

import org.example.com.domain.Sub;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubRepository extends JpaRepository<Sub, Long> {
    public Sub deleteBySubId(String subId);
}
