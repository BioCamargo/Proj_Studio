package com.beautysalon.model;
 import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
 import jakarta.persistence.*;
 import lombok.*;

 import java.util.Set;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Table(name = "\"user\"")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String username;

    @Column(unique = true)
    private String email;
    private String password;
    private boolean ativo;


    @Column(name= "image")
    private String imagem;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn (name="role_id") //porque que o "name aqui nao funciona?
    )
    @JsonIgnoreProperties("users")
    private Set<Role> roles;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

        public String getPassword() {
            return this.password;
        }
        public void setPassword(String password) {
        this.password = password;
        }

        public String getEmail() {
            return this.email;
        }
        public void setEmail(String email) {
        this.email = email;
        }

        public Set<Role> getRoles() {
            return this.roles;
        }
        public void setRoles(Set<Role> roles) {
        this.roles = roles;
        }
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getImagem() {
        return this.imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
    public boolean isAtivo() {
        return ativo;
    }
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }


    public void setEnabled(boolean b) {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}



