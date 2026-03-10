package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.service.OrderService;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final PaymentService paymentService;

    public OrderController(OrderService orderService, PaymentService paymentService) {
        this.orderService = orderService;
        this.paymentService = paymentService;
    }

    @GetMapping("/create")
    public String createOrderPage(Model model) {
        model.addAttribute("order", new OrderForm());
        return "order/create";
    }

    @GetMapping("/history")
    public String historyFormPage() {
        return "order/historyForm";
    }

    @PostMapping("/history")
    public String historyListPage(@RequestParam("author") String author, Model model) {
        model.addAttribute("orders", orderService.findAllByAuthor(author));
        return "order/historyList";
    }

    @GetMapping("/pay/{orderId}")
    public String payOrderPage(@PathVariable("orderId") String orderId, Model model) {
        Order order = orderService.findById(orderId);
        model.addAttribute("order", order);
        return "order/payOrder";
    }

    @PostMapping("/pay/{orderId}")
    public String payOrder(@PathVariable("orderId") String orderId,
                           @RequestParam("method") String method,
                           @RequestParam(value = "bankName", required = false) String bankName,
                           @RequestParam(value = "referenceCode", required = false) String referenceCode,
                           @RequestParam(value = "voucherCode", required = false) String voucherCode,
                           Model model) {
        Order order = orderService.findById(orderId);
        if (order == null) {
            return "redirect:/order/history";
        }

        Map<String, String> paymentData = new HashMap<>();
        if ("BANK_TRANSFER".equals(method)) {
            paymentData.put("bankName", bankName == null ? "" : bankName);
            paymentData.put("referenceCode", referenceCode == null ? "" : referenceCode);
        } else if ("VOUCHER".equals(method)) {
            paymentData.put("voucherCode", voucherCode == null ? "" : voucherCode);
        }

        Payment payment = paymentService.addPayment(order, method, paymentData);
        if (payment == null) {
            return "redirect:/order/pay/" + orderId;
        }

        model.addAttribute("paymentId", payment.getId());
        return "order/paySuccess";
    }

    public static class OrderForm {
        private String id;
        private String author;
        private Long orderTime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public Long getOrderTime() {
            return orderTime;
        }

        public void setOrderTime(Long orderTime) {
            this.orderTime = orderTime;
        }
    }
}
