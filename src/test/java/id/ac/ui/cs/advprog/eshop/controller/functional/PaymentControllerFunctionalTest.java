package id.ac.ui.cs.advprog.eshop.controller.functional;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PaymentControllerFunctionalTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    @Test
    void getPaymentDetailFormShouldReturnOk() throws Exception {
        mockMvc.perform(get("/payment/detail"))
                .andExpect(status().isOk());
    }

    @Test
    void getPaymentDetailByIdShouldReturnOk() throws Exception {
        Payment payment = new Payment("payment-1", "BANK_TRANSFER",
                Map.of("bankName", "BCA", "referenceCode", "REF-123"));
        when(paymentService.getPayment("payment-1")).thenReturn(payment);

        mockMvc.perform(get("/payment/detail/payment-1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("payment"));
    }

    @Test
    void getPaymentAdminListShouldReturnOk() throws Exception {
        Payment payment = new Payment("payment-1", "VOUCHER", Map.of("voucherCode", "ESHOP12345678ABCD"));
        when(paymentService.getAllPayments()).thenReturn(List.of(payment));

        mockMvc.perform(get("/payment/admin/list"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("payments"));
    }

    @Test
    void getPaymentAdminDetailByIdShouldReturnOk() throws Exception {
        Payment payment = new Payment("payment-1", "BANK_TRANSFER",
                Map.of("bankName", "BCA", "referenceCode", "REF-123"));
        when(paymentService.getPayment("payment-1")).thenReturn(payment);

        mockMvc.perform(get("/payment/admin/detail/payment-1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("payment"));
    }

    @Test
    void postPaymentAdminSetStatusShouldRedirect() throws Exception {
        Payment payment = new Payment("payment-1", "BANK_TRANSFER",
                Map.of("bankName", "BCA", "referenceCode", "REF-123"));
        when(paymentService.getPayment("payment-1")).thenReturn(payment);
        when(paymentService.setStatus(payment, "SUCCESS")).thenReturn(payment);

        mockMvc.perform(post("/payment/admin/set-status/payment-1")
                        .param("status", "SUCCESS"))
                .andExpect(status().is3xxRedirection());
    }
}
