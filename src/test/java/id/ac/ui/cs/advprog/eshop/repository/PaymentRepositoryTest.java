package id.ac.ui.cs.advprog.eshop.repository;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import id.ac.ui.cs.advprog.eshop.model.Payment;

public class PaymentRepositoryTest {
    private Map<String, String> bankTransferPayment1;
    private Map<String, String> bankTransferPayment2;
    private List<Payment> paymentData;
    private PaymentRepository paymentRepository;
    
    @BeforeEach
    void setUp(){
        paymentRepository = new PaymentRepository();

        bankTransferPayment1 = new HashMap<String,String>();
        bankTransferPayment1.put("bankName", "Mandiri");
        bankTransferPayment1.put("referenceCode", "PACIL123");

        bankTransferPayment1 = new HashMap<String,String>();
        bankTransferPayment1.put("bankName", "BRI");
        bankTransferPayment1.put("referenceCode", "ADPRO123");

        Payment payment1 = new Payment("12345", "BankTransfer", bankTransferPayment1);
        Payment payment2 = new Payment("99999", "BankTransfer", bankTransferPayment2);

        paymentData = new ArrayList<>();
        paymentData.add(payment1);
        paymentData.add(payment2);
    }

    @Test
    void testCreatePayment() {
        Payment payment1 = paymentData.get(0);
        paymentRepository.save(payment1);
        Payment payment1FromRepository = paymentRepository.findById(payment1.getId());

        assertEquals(payment1.getId(), payment1FromRepository.getId());
        assertEquals(payment1.getMethod(), payment1FromRepository.getMethod());
        assertEquals(payment1.getPaymentData(), payment1FromRepository.getPaymentData());
        assertEquals(payment1.getStatus(), payment1FromRepository.getStatus());
    }

    @Test
    void testUpdate() {
        Payment payment1 = paymentData.get(0);
        paymentRepository.save(payment1);

        bankTransferPayment1.put("referenceCode", "ADPRO456");
        Payment payment1Modif = new Payment("12345", "BankTransfer", bankTransferPayment1); 
        paymentRepository.save(payment1Modif);

        Payment payment1FromRepository = paymentRepository.findById(payment1.getId());

        assertEquals(payment1.getId(), payment1FromRepository.getId());
        assertEquals(payment1.getMethod(), payment1FromRepository.getMethod());
        assertEquals(payment1Modif.getPaymentData(), payment1FromRepository.getPaymentData());  // modified field
        assertEquals(payment1.getStatus(), payment1FromRepository.getStatus());
    }

    @Test
    void testFindById() {
        Payment payment1 = paymentData.get(0);
        paymentRepository.save(payment1);
        Payment payment2 = paymentData.get(0);
        paymentRepository.save(payment2);

        Payment payment2FromRepository = paymentRepository.findById(payment2.getId());

        assertEquals(payment2.getId(), payment2FromRepository.getId());
        assertEquals(payment2.getMethod(), payment2FromRepository.getMethod());
        assertEquals(payment2.getPaymentData(), payment2FromRepository.getPaymentData());
        assertEquals(payment2.getStatus(), payment2FromRepository.getStatus());
    }

    @Test
    void testFindByIdNotFound() {
        Payment payment1 = paymentData.get(0);
        paymentRepository.save(payment1);

        Payment payment2FromRepository = paymentRepository.findById("akhdfkajshdf");
        assertNull(payment2FromRepository);
    }

    @Test
    void testFindAll() {
        Payment payment1 = paymentData.get(0);
        paymentRepository.save(payment1);
        Payment payment2 = paymentData.get(0);
        paymentRepository.save(payment2);

        assertEquals(paymentRepository.findAll().size(), 2);
    }
}
