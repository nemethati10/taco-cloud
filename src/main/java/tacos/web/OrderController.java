package tacos.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import tacos.Order;
import tacos.User;
import tacos.data.OrderRepository;
import tacos.data.UserRepository;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {

    private OrderRepository orderRepository;

    private UserRepository userRepository;

    public OrderController(final OrderRepository orderRepository, final UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/current")
    public String orderForm(final Model model) {
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid final Order order, final Errors errors, final SessionStatus sessionStatus,
                               final Principal principal) {
        if (errors.hasErrors()) {
            return "orderForm";
        }

        final User user = userRepository.findByUsername(
                principal.getName());
        order.setUser(user);

        orderRepository.save(order);
        sessionStatus.setComplete();

        return "redirect:/";
    }
}
