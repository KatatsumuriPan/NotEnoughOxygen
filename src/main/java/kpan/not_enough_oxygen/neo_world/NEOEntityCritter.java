package kpan.not_enough_oxygen.neo_world;

public class NEOEntityCritter extends NEOEntity {

    public final Critter critter;

    public NEOEntityCritter(float x, float y, float z, Critter critter) {
        super(x, y, z, critter.initialTemperature);
        this.critter = critter;
    }

}