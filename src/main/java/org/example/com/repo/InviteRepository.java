package org.example.com.repo;

import org.example.com.domain.Employee;
import org.example.com.domain.Invite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InviteRepository extends JpaRepository<Invite, Long> {
    Optional<List<Invite>> findByTo(Employee to);

}
