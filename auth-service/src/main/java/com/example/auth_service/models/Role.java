package com.example.auth_service.models;

import com.example.auth_service.models.auditing.BaseEntityAuditing;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.HashSet;

@Entity
@Getter
@Setter
public class Role extends BaseEntityAuditing {


    private String name;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users = new HashSet<>();

    public  Role(String name){
        this.name = name;
    }
}
