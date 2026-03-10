package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
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
