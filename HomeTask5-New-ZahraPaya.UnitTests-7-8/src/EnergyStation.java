import java.util.List;

public class EnergyStation {
    String name;
    List<EnergySource> energySource;

    EnergyStation(String name, List<EnergySource> energySource) {
        this.name = name;
        this.energySource = energySource;
    }

    public List<EnergySource> getEnergySources() {
        return energySource;
    }

    public void setEnergySource(List<EnergySource> energySource) {
        this.energySource = energySource;
    }

    public String getName() {
        return name;
    }
}
