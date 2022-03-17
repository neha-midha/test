package com.globant.tc.scooter.accounts.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "account")
public class AccountEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "balance")
    private Double balance;

    @Column(name = "mercado_pago_account")
    private String mercadoPagoAccount;

    @Column(name = "is_active")
    private boolean isActive;

}
