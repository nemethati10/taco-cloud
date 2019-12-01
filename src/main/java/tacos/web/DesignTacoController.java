package tacos.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import tacos.Ingredient;
import tacos.Order;
import tacos.Taco;
import tacos.User;
import tacos.data.IngredientRepository;
import tacos.data.TacoRepository;
import tacos.data.UserRepository;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static tacos.Ingredient.Type;

/**
 * @SessionAttributes annotation specifies any model objects like the order attribute that should be kept
 * in session and available across multiple requests.
 */
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {

    private final IngredientRepository ingredientRepo;

    private final TacoRepository tacoRepository;

    private final UserRepository userRepository;

    public DesignTacoController(final IngredientRepository ingredientRepo, final TacoRepository tacoRepository,
                                final UserRepository userRepository) {
        this.ingredientRepo = ingredientRepo;
        this.tacoRepository = tacoRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String showDesignForm(final Model model, final Principal principal) {
        model.addAttribute("design", new Taco());

        final String username = principal.getName();
        final User user = userRepository.findByUsername(username);

        model.addAttribute("user", user);

        return "design";
    }

    /**
     * @ModelAttribute annotation on order() ensures that an Order object will be created
     * in the model.
     */
    @ModelAttribute(name = "order")
    public Order order() {
        return new Order();
    }

    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }

    /**
     * After checking for validation errors, processDesign() uses the injected Taco-Repository to save the taco.
     * It then adds the Taco object to the Order that’s kept in the session.
     * In fact, the Order object remains in the session and isn’t saved to the database until
     * the user completes and submits the order form.
     * <p>
     * The Order parameter is annotated with @ModelAttribute to indicate that its
     * value should come from the model and that Spring MVC shouldn’t attempt to bind
     * request parameters to it.s
     *
     * @param design
     * @param errors
     * @return
     */
    @PostMapping
    public String processDesign(@ModelAttribute("design") @Valid final Taco design, final Errors errors,
                                @ModelAttribute final Order order) {
        if (errors.hasErrors()) {
            return "design";
        }

        final Taco taco = tacoRepository.save(design);
        order.addDesign(taco);

        return "redirect:/orders/current";
    }

    @ModelAttribute
    private void addIngredientsToModel(final Model model) {
        final List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepo.findAll().forEach(i -> ingredients.add(i));

        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients, type));
        }
    }

    private List<Ingredient> filterByType(final List<Ingredient> ingredients, final Type type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }

}
