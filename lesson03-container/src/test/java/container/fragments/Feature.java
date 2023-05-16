package container.fragments;

public class Feature {
    private final String name;

    public Feature(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
