package com.example.togepi.repository;

import com.example.togepi.model.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface PaymentMethodRepository  extends JpaRepository<PaymentMethod, Long> {
}
