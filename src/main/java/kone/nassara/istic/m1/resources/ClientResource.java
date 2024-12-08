package kone.nassara.istic.m1.resources;

import kone.nassara.istic.m1.database.Restaurant;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import java.util.List;
import java.util.stream.Collectors;

public class ClientResource extends ServerResource {

    @Get
    public List<String> listClients() {
        System.out.println("GET /clients appelé"); // Log pour le débogage
        Restaurant restaurant = (Restaurant) getContext().getAttributes().get("restaurant");

        if (restaurant == null) {
            System.err.println("Restaurant non trouvé dans le contexte");
            throw new IllegalStateException("Restaurant non initialisé");
        }

        return restaurant.getClients().stream()
                .map(client -> "Client " + client.getId() + " - Etat: " + client.getState())
                .collect(Collectors.toList());
    }
}
