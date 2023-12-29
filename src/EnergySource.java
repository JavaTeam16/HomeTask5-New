public class EnergySource {
    String name;
    Number capacity;

    EnergySource(String name, Number capacity) {
        this.name = name;
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public Number getCapacity() {
        return capacity;
    }
}
