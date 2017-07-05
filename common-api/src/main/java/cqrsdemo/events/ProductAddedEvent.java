package cqrsdemo.events;

/**
 * Created by benwilcock on 18/04/2017.
 */
public class ProductAddedEvent extends AbstractEvent {

    private String name;

    public ProductAddedEvent() {
    }

    public ProductAddedEvent(String id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
