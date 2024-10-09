package kpan.not_enough_oxygen.capability.capabilities;

import kpan.not_enough_oxygen.capability.IMyCapability;
import kpan.not_enough_oxygen.neo_world.Simulation;

public interface ICapabilityWorld extends IMyCapability<ICapabilityWorld> {

    Simulation getSimulation();

}
