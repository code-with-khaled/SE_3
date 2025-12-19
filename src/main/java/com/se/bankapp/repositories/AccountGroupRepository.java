package com.se.bankapp.repositories;

import com.se.bankapp.models.AccountGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountGroupRepository extends JpaRepository<AccountGroup, Long> {
}
