package com.api.curso.api_curso.module.purchase.model.dto;

import java.util.UUID;
import com.api.curso.api_curso.module.purchase.model.entity.PurchaseEntity;

public record CreatePurchaseDTO(UUID courseId, String paymentMethod) {
    
    public PurchaseEntity toEntity() {
        return new PurchaseEntity(
            courseId,
            paymentMethod
        );
    }
}
