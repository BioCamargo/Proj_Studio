package com.beautysalon.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public class UserDTO {
    private Long id;

    @NotBlank(message = "Name is required")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Email inválid")
    private String email;

    @NotBlank(message = "Password required")
    @Size(min = 6, message = "At least 6 characters")
    private String password;

    //@NotEmpty(message = "At least one role must be assigned")
    private Set<Long> roleIds;

    private MultipartFile imagem;
    private String imagemUrl;

    private Set<RoleDTO> roles;


    // Getters e Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Long> getRoleIds() {
        return roleIds;
    }
    public void setRoleIds(Set<Long> roleIds) {
        this.roleIds = roleIds;
    }

    public MultipartFile getImagem() {return imagem;}
    public void setImagem(MultipartFile imagem) {this.imagem = imagem;}

    public String getImagemUrl() {return imagemUrl;}
    public void setImagemUrl(String imagemUrl) {this.imagemUrl = imagemUrl;}

    public Set<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleDTO> roles) {
        this.roles = roles;
    }

}
