package no.shoppifly;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController()
public class ShoppingCartController implements ApplicationListener<ApplicationReadyEvent> {

    private final Map<String, Cart> shoppingCarts = new HashMap<>();
    private final Map<String, Cart> metricsMap = new HashMap<>();
    private MeterRegistry meterRegistry;

    @Autowired
    private final CartService cartService;

    public ShoppingCartController(CartService cartService, MeterRegistry meterRegistry) {
        this.cartService = cartService;
        this.meterRegistry = meterRegistry;
    }

    @GetMapping(path = "/cart/{id}")
    public Cart getCart(@PathVariable String id) {
        meterRegistry.counter("cart").increment();
        return cartService.getCart(id);
    }

    /**
     * Checks out a shopping cart. Removes the cart, and returns an order ID
     *
     * @return an order ID
     */
    @Timed(value = "checkout_latency")
    @PostMapping(path = "/cart/checkout")
    public String checkout(@RequestBody Cart cart) {
        String checkout = cartService.checkout(cart);
        shoppingCarts.put(cart.getId(), cart);
        metricsMap.remove(cart.getId());

        meterRegistry.counter("checkout_carts").increment();
        return cartService.checkout(cart);
    }

    /**
     * Updates a shopping cart, replacing it's contents if it already exists. If no cart exists (id is null)
     * a new cart is created.
     *
     * @return the updated cart
     */
    @Timed
    @PostMapping(path = "/cart")
    public Cart updateCart(@RequestBody Cart cart) {
        metricsMap.put(cart.getId(), cart);
        meterRegistry.counter("update_carts").increment();
        return cartService.update(cart);
    }

    /**
     * return all cart IDs
     *
     * @return
     */
    @GetMapping(path = "/carts")
    public List<String> getAllCarts() {
        meterRegistry.counter("all_carts").increment();
        return cartService.getAllsCarts();
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        Gauge.builder("carts", metricsMap,
                b -> b.values().size()).register(meterRegistry);
        Gauge.builder("checkouts", shoppingCarts,
                b -> b.values().size()).register(meterRegistry);
        Gauge.builder("total_carts", cartService,
                b -> b.total()).register(meterRegistry);
    }
}