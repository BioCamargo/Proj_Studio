package com.beautysalon.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Data
//@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)  // Garante que nomes de roles sejam únicos
    private String nome;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private Set<User> users;

    public Role(String nome) {
        this.nome = nome;
    }

    public String getNome() {return nome;}
    public void setNome(String nome) {this.nome = nome;}

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public Set<User> getUsers() {return users;}
    public void setUsers(Set<User> users) {this.users = users;}
    public Role() {}

//    public Role orElseThrow(Object roleAdminNãoEncontrada)
//    {
//        return null;
//    }
}
