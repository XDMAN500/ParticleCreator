package dev.fumaz.particlecreator.gui;

import dev.fumaz.particlecreator.Coordinate;
import dev.fumaz.particlecreator.particle.Particle;

public class Action {

    private final Coordinate coordinate;
    private final Particle oldParticle;
    private final Particle newParticle;

    public Action(Coordinate coordinate, Particle oldParticle, Particle newParticle) {
        this.coordinate = coordinate;
        this.oldParticle = oldParticle;
        this.newParticle = newParticle;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public Particle getOldParticle() {
        return oldParticle;
    }

    public Particle getNewParticle() {
        return newParticle;
    }

    public void undo(Particle[][] particles) {
        particles[coordinate.getY()][coordinate.getX()] = oldParticle;
    }

    public void redo(Particle[][] particles) {
        particles[coordinate.getY()][coordinate.getX()] = newParticle;
    }

}
