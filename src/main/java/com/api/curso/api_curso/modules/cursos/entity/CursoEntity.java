package com.api.curso.api_curso.modules.cursos.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.api.curso.api_curso.modules.users.entity.UserEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PreUpdate;

@Entity(name = "cursos")
public class CursoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Schema(description = "Nome do curso", example = "Java")
    private String name;
    @Schema(description = "Descrição do curso", example = "ensino")
    private String category;
    private boolean active = true;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)//utilizado para nao alterar a datos de criacao
    private LocalDateTime createdAt;

    @CreationTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public CursoEntity(String name, String category) {
        this.name = name;
        this.category = category;
    }

    public CursoEntity() {
    } 
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public UserEntity getUser() {
        return user;
    }

    public String getCategory() {
        return category;
    }

    public boolean isActive() {
        return active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }



}
