package dev.fumaz.particlecreator.template;

import dev.fumaz.particlecreator.particle.Particle;
import dev.fumaz.particlecreator.particle.ParticleType;

import java.awt.Color;

public abstract class Template {

    public abstract boolean[][] getPixels();

    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public String toString() {
        return getName();
    }

    public void load(Particle[][] pixels) {
        for (int y = 0; y < pixels.length; y++) {
            for (int x = 0; x < pixels[y].length; x++) {
                boolean p = getPixels()[y][x];

                pixels[y][x] = !p ? null : new Particle(ParticleType.REDSTONE, Color.BLACK);
            }
        }
    }

}
