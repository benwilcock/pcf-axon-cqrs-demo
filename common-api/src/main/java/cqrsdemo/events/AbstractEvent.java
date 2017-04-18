package cqrsdemo.events;

/**
 * Created by benwilcock on 18/04/2017.
 */
public abstract class AbstractEvent {

    private String id;

    public AbstractEvent() {
    }

    public AbstractEvent(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
