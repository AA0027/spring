package org.example.com.repo;

import org.example.com.domain.Employee;
import org.example.com.domain.Invite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface InviteRepository extends JpaRepository<Invite, Long> {
    Optional<List<Invite>> findInvitesByTo(Employee to);
    @Transactional
    void deleteByCode(String code);

    Optional<Invite> findInvitesByCodeAndTo(String code, Employee to);

}
