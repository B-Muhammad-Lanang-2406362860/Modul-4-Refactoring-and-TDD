package id.ac.ui.cs.advprog.eshop.controller.functional;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.service.OrderService;
import id.ac.ui.cs.advprog.eshop.service.PaymentServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerFunctionalTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @MockBean
    private PaymentServiceImpl paymentServiceImpl;

    @Test
    void getCreateOrderPageShouldReturnOrderCreateView() throws Exception {
        mockMvc.perform(get("/order/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("order/create"));
    }

    @Test
    void getOrderHistoryFormShouldReturnHistoryFormView() throws Exception {
        mockMvc.perform(get("/order/history"))
                .andExpect(status().isOk())
                .andExpect(view().name("order/historyForm"));
    }

    @Test
    void postOrderHistoryWithAuthorShouldReturnHistoryListAndOrdersModel() throws Exception {
        Order order = mock(Order.class);
        List<Order> orders = List.of(order);

        when(orderService.findAllByAuthor("Lanang")).thenReturn(orders);

        mockMvc.perform(post("/order/history")
                        .param("author", "Lanang"))
                .andExpect(status().isOk())
                .andExpect(view().name("order/historyList"))
                .andExpect(model().attributeExists("orders"))
                .andExpect(model().attribute("orders", orders));

        verify(orderService).findAllByAuthor("Lanang");
    }

    @Test
    void postOrderHistoryWithBlankAuthorShouldStillReturnHistoryListAndEmptyOrders() throws Exception {
        when(orderService.findAllByAuthor(""))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(post("/order/history")
                        .param("author", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("order/historyList"))
                .andExpect(model().attributeExists("orders"))
                .andExpect(model().attribute("orders", Collections.emptyList()));

        verify(orderService).findAllByAuthor("");
    }

    @Test
    void postOrderHistoryWithoutAuthorShouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/order/history"))
                .andExpect(status().isBadRequest());
    }
}
