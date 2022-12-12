package no.shoppifly;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CartService {

    float total();

    Cart getCart(String id);

    Cart update(Cart cart);

    String checkout(Cart cart);

    List<String> getAllsCarts();
}
