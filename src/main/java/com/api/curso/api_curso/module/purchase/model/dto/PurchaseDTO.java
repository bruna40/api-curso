package com.api.curso.api_curso.module.purchase.model.dto;

import java.util.UUID;
import com.api.curso.api_curso.module.purchase.model.entity.PurchaseEntity;

public record PurchaseDTO(Long id, UUID userId, UUID courseId, String methodPayment, Double priceCurso ) {
    
    public static PurchaseDTO fromEntity(PurchaseEntity purchaseEntity) {
        return new PurchaseDTO(
            purchaseEntity.getId(),
            purchaseEntity.getUser().getId(),
            purchaseEntity.getCourse().getId(),
            purchaseEntity.getPaymentMethod(),
            purchaseEntity.getCourse().getPrice()
        );
    }



}
