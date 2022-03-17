package com.globant.tc.scooter.accounts.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "accounts_users")
public class AccountUserMappingEntity implements Serializable {

    @EmbeddedId
    private AccountUserMappingId accountUserMappingId;
}
