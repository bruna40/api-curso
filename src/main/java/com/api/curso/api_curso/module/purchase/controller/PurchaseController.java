package com.api.curso.api_curso.module.purchase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.curso.api_curso.module.purchase.model.entity.PurchaseEntity;
import com.api.curso.api_curso.module.purchase.useCase.PurchaseUseCase;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {
    
    @Autowired
    private PurchaseUseCase purchaseUseCase;
}
