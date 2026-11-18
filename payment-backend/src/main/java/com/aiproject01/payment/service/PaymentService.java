
package com.aiproject01.payment.service;

import com.aiproject01.payment.entity.Payment;
import com.aiproject01.payment.repository.PaymentRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    // =====================================
    // 결제 요청 생성
    // =====================================

    @Transactional
    public Payment createPayment(String username, int amount) {

        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("사용자 이름이 필요합니다.");
        }

        if (amount <= 0) {
            throw new IllegalArgumentException("결제 금액은 0보다 커야 합니다.");
        }

        String orderId = "ORDER-" + System.currentTimeMillis();

        Payment payment = new Payment(
                orderId,
                username,
                amount,
                "PENDING"
        );

        return paymentRepository.save(payment);
    }

    // =====================================
    // 결제 승인
    // =====================================

    @Transactional
    public Payment approvePayment(Long paymentId) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() ->
                        new IllegalArgumentException("결제 정보를 찾을 수 없습니다.")
                );

        if (!"PENDING".equals(payment.getStatus())) {
            throw new IllegalStateException("승인할 수 없는 결제입니다.");
        }

        payment.setStatus("SUCCESS");

        return payment;
    }

    // =====================================
    // 사용자별 결제 내역 조회
    // =====================================

    public List<Payment> getPayments(String username) {

        return paymentRepository.findByUsername(username);
    }

    // =====================================
    // 결제 취소
    // =====================================

    @Transactional
    public Payment cancelPayment(Long paymentId) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() ->
                        new IllegalArgumentException("결제 정보를 찾을 수 없습니다.")
                );

        String status = payment.getStatus();

        if (!"PENDING".equals(status) && !"SUCCESS".equals(status)) {
            throw new IllegalStateException("취소할 수 없는 결제입니다.");
        }

        payment.setStatus("CANCELED");

        return payment;
    }
}
