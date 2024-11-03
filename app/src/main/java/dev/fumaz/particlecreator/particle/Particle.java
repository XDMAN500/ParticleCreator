package dev.fumaz.particlecreator.particle;

import java.awt.Color;
import java.util.Objects;

public class Particle {

    private final ParticleType type;
    private final Color color;

    public Particle(ParticleType type, Color color) {
        this.type = type;
        this.color = color;
    }

    public ParticleType getType() {
        return type;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Particle)) {
            return false;
        }

        Particle particle = (Particle) o;
        return type == particle.type && Objects.equals(color, particle.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, color);
    }

}
