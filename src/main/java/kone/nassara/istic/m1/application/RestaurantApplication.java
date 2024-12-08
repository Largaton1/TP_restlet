package kone.nassara.istic.m1.application;

import kone.nassara.istic.m1.resources.BuffetResource;
import kone.nassara.istic.m1.resources.ClientResource;
import kone.nassara.istic.m1.resources.NewClientResource;
import org.restlet.Application;
import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.routing.Router;

public class RestaurantApplication extends Application {

    public RestaurantApplication(Context context) {
        super(context);
    }

    @Override
    public synchronized Restlet createInboundRoot() {
        Router router = new Router(getContext());

        router.attach("/clients", ClientResource.class);
        router.attach("/clients/new", NewClientResource.class);
        router.attach("/buffet", BuffetResource.class);

        return router;
    }
}
