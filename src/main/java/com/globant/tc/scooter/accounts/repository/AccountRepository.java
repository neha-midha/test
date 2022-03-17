package com.globant.tc.scooter.accounts.repository;

import com.globant.tc.scooter.accounts.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Integer> {

    AccountEntity findByIdAndIsActive(Integer accountId, boolean isActive);
}
