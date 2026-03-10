package id.ac.ui.cs.advprog.eshop.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.InvalidArgumentException;

public class PaymentTest {

    private Map<String, String> bankTransferPayment;

    @BeforeEach
    void setUp() {
        bankTransferPayment = new HashMap<String,String>();
        bankTransferPayment.put("bankName", "Mandiri");
        bankTransferPayment.put("referenceCode", "PACIL123");
    }
    
    @Test
    void testCreatePayment() {
        String id = "12345";
        String method = "BankPayment";
        Payment payment = new Payment(id, method, bankTransferPayment);

        assertEquals(payment.getId(), id);
        assertEquals(payment.getMethod(), method);
        assertEquals(payment.getPaymentData(), bankTransferPayment);
        assertEquals(payment.getStatus(), "PENDING");
    }

    @Test
    void testSetStatus() {
        String id = "12345";
        String method = "BankPayment";
        Payment payment = new Payment(id, method, bankTransferPayment);

        payment.setStatus("SUCCESS");
        assertEquals(payment.getStatus(), "SUCCESS");
    }

    @Test
    void testSetStatusInvalid() {
        String id = "12345";
        String method = "BankPayment";
        Payment payment = new Payment(id, method, bankTransferPayment);

        assertThrows(InvalidArgumentException.class, () -> {
            payment.setStatus("RANDOMAJA");
        });
    }
}
