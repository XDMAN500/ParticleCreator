package dev.fumaz.particlecreator.particle;

import java.util.Map;

public class ParticlePalette {

    private final Map<Character,Particle> palette;

    public ParticlePalette(Map<Character,Particle> palette) {
        this.palette = palette;
    }

    public boolean hasParticleKey(char key) {
        return palette.containsKey(key);
    }

    public Particle getParticle(char key) {
        return palette.get(key);
    }

    /*public static ParticlePalette loadFromConfiguration(ConfigurationSection configurationSection) {
        Map<Character,ParticleBuilder> palette = new HashMap<>();
        for (String key : configurationSection.getKeys(false)) {
            palette.put(key.charAt(0), createParticleFromLine(configurationSection.getString(key)));
        }
        return new ParticlePalette(palette);
    }*/

   /* private static ParticleBuilder createParticleFromLine(String line) {
        String parts[] = line.split("\\|");
        String particleType = parts[0];

        Particle particle = Arrays.stream(Particle.values())
                                .filter(part -> part.getKey().getKey().startsWith(
                                    particleType) || part.getKey().asString().startsWith(particleType))
                                .findAny().orElseThrow( () -> new IllegalArgumentException("Cannot find particle type for key \"%s\"".formatted(particleType)));


        ParticleBuilder builder = new ParticleBuilder(particle);
        for (int index = 1; index < parts.length; index += 1) {
            String part = parts[index];

            if (particle == Particle.DUST) {
                Color color = Color.fromRGB(Integer.parseInt(part.substring(1), 16));
                builder.color(color, 1.3F);
            }

            if (part.startsWith("#") && particle == Particle.DUST) {
                Color color = Color.fromRGB(Integer.parseInt(part.substring(1), 16));
                builder.color(color, 1.3F);
            }

            if (part.startsWith("speed=")) {
                double speed = Double.parseDouble(part.substring("speed=".length()));
                builder.extra(speed);
            }
        }
        return builder;
    }*/
}
