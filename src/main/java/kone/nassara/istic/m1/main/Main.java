package kone.nassara.istic.m1.main;

import kone.nassara.istic.m1.application.RestaurantApplication;
import kone.nassara.istic.m1.database.Restaurant;
import org.restlet.Component;
import org.restlet.Context;
import org.restlet.data.Protocol;

public class Main {
    public static void main(String[] args) throws Exception {
        Component component = new Component();
        Context context = component.getContext().createChildContext();
        component.getServers().add(Protocol.HTTP, 8181);

        Restaurant restaurant = new Restaurant();
        context.getAttributes().put("restaurant", restaurant);

        RestaurantApplication app = new RestaurantApplication(context);
        component.getDefaultHost().attach(app);

        component.start();
    }
}
