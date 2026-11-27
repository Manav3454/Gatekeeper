package com.GateKeeper.Api_Management_Service.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.processing.Generated;
@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor


public class Role
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
