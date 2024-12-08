package kone.nassara.istic.m1.resources;

import kone.nassara.istic.m1.database.Restaurant;
import kone.nassara.istic.m1.internals.Compartiment;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import java.util.HashMap;
import java.util.Map;

public class BuffetResource extends ServerResource {

    private final Restaurant restaurant;

    public BuffetResource(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Get
    public Map<String, Integer> getBuffetStatus() {
        // Obtenir les quantités restantes pour chaque compartiment
        Map<String, Integer> status = new HashMap<>();
        for (Compartiment compartiment : Compartiment.values()) {
            status.put(compartiment.name(), restaurant.getBuffet().getQuantite(compartiment));
        }
        return status;
    }
}
