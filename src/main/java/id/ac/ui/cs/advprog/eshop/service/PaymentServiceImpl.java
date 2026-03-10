package id.ac.ui.cs.advprog.eshop.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    OrderServiceImpl orderService;

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
        String paymentId = order.getId();
        if (paymentRepository.findById(paymentId) != null) {
            return null;
        }

        Payment payment = new Payment(paymentId, method, paymentData);
        paymentRepository.save(payment);

        if (isVoucherPayment(paymentData)) {
            String voucherCode = paymentData.get("voucherCode");
            if (isValidVoucherCode(voucherCode)){
                payment = setStatus(payment, PaymentStatus.SUCCESS.getValue());
            } else {
                payment = setStatus(payment, PaymentStatus.REJECTED.getValue());
            }
        }

        return payment;
    }

    private boolean isVoucherPayment(Map<String, String> paymentData) {
        boolean haveOneKeyValuePair = (paymentData.size() == 1);
        boolean haveVoucherCodeKey = (paymentData.get("voucherCode") != null);
        return haveOneKeyValuePair && haveVoucherCodeKey;
    }

    private boolean isValidVoucherCode(String voucherCode){
        if (voucherCode.length() != 16) return false;
        if (!voucherCode.startsWith("ESHOP")) return false;
        if (voucherCode.chars().filter(c -> Character.isDigit(c)).count() != 8) return false;
        return true;
    }

    @Override
    public Payment setStatus(Payment payment, String status) {
        // if (paymentRepository.findById(payment.getId()) == null) {
        //     return null;
        // }

        if (status.equals(PaymentStatus.SUCCESS.getValue())) {
            payment.setStatus(PaymentStatus.SUCCESS.getValue());
            paymentRepository.save(payment);
            orderService.updateStatus(payment.getId(), OrderStatus.SUCCESS.getValue());
            return payment;
        } else if (status.equals(PaymentStatus.REJECTED.getValue())) {
            payment.setStatus(PaymentStatus.REJECTED.getValue());
            paymentRepository.save(payment);
            orderService.updateStatus(payment.getId(), OrderStatus.FAILED.getValue());
            return payment;
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Payment getPayment(String paymentId) {
        return paymentRepository.findById(paymentId);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}
