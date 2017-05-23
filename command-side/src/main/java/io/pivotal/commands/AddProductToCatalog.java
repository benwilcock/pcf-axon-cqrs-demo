package io.pivotal.commands;

public class AddProductToCatalog {

    private final String id;
    private final String name;

    public AddProductToCatalog(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
