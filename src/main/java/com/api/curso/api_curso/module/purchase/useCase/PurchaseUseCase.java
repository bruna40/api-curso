package com.api.curso.api_curso.module.purchase.useCase;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.api.curso.api_curso.module.purchase.model.entity.PurchaseEntity;
import com.api.curso.api_curso.module.purchase.repository.PurchaseRepository;
import com.api.curso.api_curso.module.users.model.entity.UserEntity;
import com.api.curso.api_curso.module.users.useCases.UserUseCase;


@Service
public class PurchaseUseCase {
    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private UserUseCase userUseCase;

    public PurchaseEntity createPurchase(PurchaseEntity purchase, UUID userId) {
        UserEntity user = userUseCase.getUserById(userId);

        purchase.setUser(user);
        return purchaseRepository.save(purchase);
    }
}
