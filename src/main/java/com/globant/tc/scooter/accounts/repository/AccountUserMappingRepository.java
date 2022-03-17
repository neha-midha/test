package com.globant.tc.scooter.accounts.repository;

import com.globant.tc.scooter.accounts.entity.AccountUserMappingEntity;
import com.globant.tc.scooter.accounts.entity.AccountUserMappingId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountUserMappingRepository extends JpaRepository<AccountUserMappingEntity, AccountUserMappingId> {
}
