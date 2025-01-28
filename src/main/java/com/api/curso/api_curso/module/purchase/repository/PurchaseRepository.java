package com.api.curso.api_curso.module.purchase.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.api.curso.api_curso.module.purchase.model.entity.PurchaseEntity;


@Repository
public interface PurchaseRepository extends JpaRepository<PurchaseEntity, UUID> {
    
}
