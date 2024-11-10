package dev.fumaz.particlecreator.gui;

import dev.fumaz.particlecreator.particle.ParticleCanvas;
import dev.fumaz.particlecreator.particle.ParticleOrientation;

public class Project {
    ParticleCanvas particleCanvas;
    ParticleOrientation particleOrientation;

    public ParticleCanvas getParticleCanvas() {
        return particleCanvas;
    }

    public void setParticleCanvas(ParticleCanvas particleCanvas) {
        this.particleCanvas = particleCanvas;
    }

    public ParticleOrientation getParticleOrientation() {
        return particleOrientation;
    }

    public void setParticleOrientation(ParticleOrientation particleOrientation) {
        this.particleOrientation = particleOrientation;
    }
}
