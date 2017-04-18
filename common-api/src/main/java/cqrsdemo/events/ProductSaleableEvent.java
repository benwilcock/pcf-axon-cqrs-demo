package cqrsdemo.events;

/**
 * Created by benwilcock on 18/04/2017.
 */
public class ProductSaleableEvent extends AbstractEvent{

    public ProductSaleableEvent() {
    }

    public ProductSaleableEvent(String id) {
        super(id);
    }
}
