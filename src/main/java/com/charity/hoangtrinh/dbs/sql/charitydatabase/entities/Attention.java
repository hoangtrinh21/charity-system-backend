package com.charity.hoangtrinh.dbs.sql.charitydatabase.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "attention")
public class Attention {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id", nullable = false)
    private UserAccount userAccount;

    @Column(name = "object_id", nullable = false)
    private Integer objectId;

    @Column(name = "object_type", nullable = false)
    private Integer objectType;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

}