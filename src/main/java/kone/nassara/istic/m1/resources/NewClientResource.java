package kone.nassara.istic.m1.resources;

import kone.nassara.istic.m1.database.Restaurant;
import kone.nassara.istic.m1.internals.Client;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

public class NewClientResource extends ServerResource {

    @Post
    public String addClient() {
        // Retrieve the shared Restaurant instance from the application context
        Restaurant restaurant = (Restaurant) getContext().getAttributes().get("restaurant");

        // Generate a new ID and add the client
        int id = restaurant.getClients().size() + 1;
        Client newClient = new Client(id, restaurant);
        newClient.start();
        return "Client " + id + " ajouté et démarré.";
    }
}
