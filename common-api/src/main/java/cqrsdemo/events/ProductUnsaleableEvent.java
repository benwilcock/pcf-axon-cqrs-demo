package cqrsdemo.events;

/**
 * Created by benwilcock on 18/04/2017.
 */
public class ProductUnsaleableEvent extends AbstractEvent{

    public ProductUnsaleableEvent() {
    }

    public ProductUnsaleableEvent(String id) {
        super(id);
    }
}
