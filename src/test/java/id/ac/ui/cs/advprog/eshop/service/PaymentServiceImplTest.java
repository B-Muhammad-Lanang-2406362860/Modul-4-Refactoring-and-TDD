package id.ac.ui.cs.advprog.eshop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceImplTest {
    
    @InjectMocks
    PaymentServiceImpl paymentServiceImpl;

    @Mock
    PaymentRepository paymentRepository;    
    
    @Mock
    OrderServiceImpl orderService;

    private Map<String, String> bankTransferPayment1;
    private Map<String, String> bankTransferPayment2;
    
    Product product1;
    Order order1;
    Payment payment1, payment2;
    
    @BeforeEach
    void setUp(){
        bankTransferPayment1 = new HashMap<String,String>();
        bankTransferPayment1.put("bankName", "Mandiri");
        bankTransferPayment1.put("referenceCode", "PACIL123");

        bankTransferPayment1 = new HashMap<String,String>();
        bankTransferPayment1.put("bankName", "BRI");
        bankTransferPayment1.put("referenceCode", "ADPRO123");

        payment1 = new Payment("12345", "BankTransfer", bankTransferPayment1);
        payment2 = new Payment("99999", "BankTransfer", bankTransferPayment2);


        product1 = new Product();
        product1.setProductId("e1e632fa-085d-4320-ad66-6c719315627b");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);

        List<Product> products = new ArrayList<>();
        products.add(product1);
        order1 = new Order("12345",
                products, 1708560000L, "Safira Sudrajat");
    }

    @Test
    void testAddPayment(){
        doReturn(null).when(paymentRepository).findById(order1.getId());
        doReturn(payment1).when(paymentRepository).save(any(Payment.class));

        Payment payment = paymentServiceImpl.addPayment(order1, "BankTransfer", bankTransferPayment1);

        assertEquals(payment1.getId(), payment.getId());
        assertEquals(payment1.getMethod(), payment.getMethod());
        assertEquals(payment1.getPaymentData(), payment.getPaymentData());
        assertEquals(payment1.getStatus(), payment.getStatus());
    }

    @Test 
    void testAddPaymentIfAlreadyExist() {
        doReturn(payment1).when(paymentRepository).findById(order1.getId());

        Payment payment = paymentServiceImpl.addPayment(order1, "BankTransfer", bankTransferPayment1);

        assertNull(payment);
        verify(paymentRepository, times(0)).save(any(Payment.class));
    }

    @Test 
    void testSetStatusSuccess(){
        doReturn(payment1).when(paymentRepository).findById(payment1.getId());
        payment1.setStatus("SUCCESS");
        doReturn(payment1).when(paymentRepository).save(any(Payment.class));

        doReturn(null).when(orderService).updateStatus(anyString(), anyString());

        Payment payment = paymentServiceImpl.setStatus(payment1, PaymentStatus.SUCCESS.getValue());

        assertEquals(payment1.getId(), payment.getId());
        assertEquals(payment1.getMethod(), payment.getMethod());
        assertEquals(payment1.getPaymentData(), payment.getPaymentData());
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
        verify(orderService, times(1)).updateStatus(anyString(), anyString());
    }

    @Test 
    void testSetStatusRejected(){
        doReturn(payment1).when(paymentRepository).findById(payment1.getId());
        payment1.setStatus("REJECTED");
        doReturn(payment1).when(paymentRepository).save(any(Payment.class));

        doReturn(null).when(orderService).updateStatus(anyString(), anyString());

        Payment payment = paymentServiceImpl.setStatus(payment1, PaymentStatus.REJECTED.getValue());

        assertEquals(payment1.getId(), payment.getId());
        assertEquals(payment1.getMethod(), payment.getMethod());
        assertEquals(payment1.getPaymentData(), payment.getPaymentData());
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
        verify(orderService, times(1)).updateStatus(anyString(), anyString());
    }

    @Test 
    void testSetStatusIfStatusInvalid(){
        doReturn(payment1).when(paymentRepository).findById(payment1.getId());
        
        assertThrows(IllegalArgumentException.class, () -> {
            paymentServiceImpl.setStatus(payment1, "ADALAHPOKOKNYA");
        });

        verify(orderService, times(0)).updateStatus(anyString(), anyString());
    }

    @Test 
    void testSetStatusIfPaymentInvalid(){
        doReturn(null).when(paymentRepository).findById(payment1.getId());

        Payment payment = paymentServiceImpl.setStatus(payment1, PaymentStatus.REJECTED.getValue());

        assertNull(payment);
        verify(orderService, times(0)).updateStatus(anyString(), anyString());
        verify(paymentRepository, times(0)).save(any(Payment.class));
    }

    @Test
    void testGetPayment() {
        doReturn(payment1).when(paymentRepository).findById(payment1.getId());

        Payment payment = paymentServiceImpl.getPayment(payment1.getId());

        assertEquals(payment1.getId(), payment.getId());
        assertEquals(payment1.getMethod(), payment.getMethod());
        assertEquals(payment1.getPaymentData(), payment.getPaymentData());
        assertEquals(payment1.getStatus(), payment.getStatus());
    }

    @Test
    void testGetPaymentNotFound() {
        doReturn(null).when(paymentRepository).findById(payment1.getId());

        Payment payment = paymentServiceImpl.getPayment(payment1.getId());

        assertNull(payment);
    }

    @Test
    void testGetAllPayment() {
        List<Payment> paymentList = new ArrayList<>();
        paymentList.add(payment1);
        paymentList.add(payment2);
        doReturn(paymentList).when(paymentRepository).findAll();

        List<Payment> paymentListFromService = paymentServiceImpl.getAllPayments();

        assertEquals(2, paymentListFromService.size());
    }
}
