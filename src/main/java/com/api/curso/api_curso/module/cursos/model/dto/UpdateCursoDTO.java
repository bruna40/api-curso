package com.api.curso.api_curso.module.cursos.model.dto;

import java.util.UUID;

import com.api.curso.api_curso.module.cursos.model.enums.CategoryEnum;


public class UpdateCursoDTO {

    private UUID id;
    private String name;
    private CategoryEnum category;
    private Double price;

    public UpdateCursoDTO(UUID id,String name, CategoryEnum category, Double price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public Double getPrice() {
        return price;
    }

    public void setId(UUID id) {
        this.id = id;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(CategoryEnum category) {
        this.category = category;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
