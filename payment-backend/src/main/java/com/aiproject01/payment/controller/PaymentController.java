
package com.aiproject01.payment.controller;

import com.aiproject01.payment.entity.Payment;
import com.aiproject01.payment.service.PaymentService;
import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public Payment createPayment(@RequestBody PaymentRequest request) {

        return paymentService.createPayment(
            request.getUsername(),
            request.getAmount()
        );
    }

    @PostMapping("/{paymentId}/approve")
    public Payment approvePayment(@PathVariable Long paymentId) {
        
        return paymentService.approvePayment(paymentId);
    }

    @GetMapping("/{username}")
    public List<Payment> getPayments(@PathVariable String username) {

        return paymentService.getPayments(username);
    }

    @PostMapping("/{paymentId}/cancel")
    public Payment cancelPayment(@PathVariable Long paymentId) {

        return paymentService.cancelPayment(paymentId);
    }
    public static class PaymentRequest {

        private String username;
        private int amount;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }
    }
}
