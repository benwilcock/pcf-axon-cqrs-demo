package cqrsdemo.events;

/**
 * Created by benwilcock on 28/04/2017.
 */
public class ProductNameChangeEvent extends AbstractEvent {


    private String name;

    public ProductNameChangeEvent() {
    }

    public ProductNameChangeEvent(String id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
