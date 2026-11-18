package com.aiproject01.payment.repository;
import java.util.List;
import com.aiproject01.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByUsername(String username);

}