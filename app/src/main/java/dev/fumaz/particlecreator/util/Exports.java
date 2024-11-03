package dev.fumaz.particlecreator.util;

import dev.fumaz.particlecreator.gui.Gui;
import dev.fumaz.particlecreator.particle.Particle;
import dev.fumaz.particlecreator.particle.ParticleType;

import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public final class Exports {

    public static void save(Particle[][] pixels, Map<Character, Particle> mappings, File file) throws IOException {
        FileWriter writer = new FileWriter(file, false);

        writer.append("# Particle Creator\n");
        writer.append("# Made with <3 by Fumaz\n\n");
        writer.append("version: " + Gui.VERSION + "\n\n");
        writer.append("mappings:\n");

        mappings.forEach((character, particle) -> {
            try {
                writer.append("  ")
                        .append(String.valueOf(character))
                        .append(": ")
                        .append(serializeParticle(particle))
                        .append("\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        writer.append("\n");
        writer.append("pixels:\n");

        for (int y = 0; y < pixels.length; y++) {
            writer.append("  ")
                    .append(String.valueOf(y))
                    .append(": ");

            for (int x = 0; x < pixels[y].length; x++) {
                Particle particle = pixels[y][x];
                Character character = mappings.entrySet()
                        .stream()
                        .filter(entry -> (entry.getValue() == null && particle == null) || (entry.getValue() != null && entry.getValue().equals(particle)))
                        .findFirst()
                        .map(Map.Entry::getKey)
                        .orElse(null);

                if (character == null) {
                    throw new IllegalStateException("No character found for particle " + particle);
                }

                writer.append(String.valueOf(character));
            }

            writer.append("\n");
        }

        writer.close();
    }

    public static void open(Particle[][] pixels, File file) throws IOException {
        Scanner scanner = new Scanner(file);
        scanner.useDelimiter("\n");

        int count = 0;
        boolean isMappings = false;
        boolean isPixels = false;

        Map<Character, Particle> mappings = new HashMap<>();

        for (; scanner.hasNext(); count++) {
            String line = scanner.nextLine();

            // Skip comments
            if (line.startsWith("#") || line.trim().isEmpty()) {
                continue;
            }

            if (line.startsWith("mappings:")) {
                isMappings = true;
                continue;
            }

            if (line.startsWith("pixels:")) {
                isPixels = true;
                isMappings = false;
            }

            if (isMappings) {
                String[] data = line.trim().split(":");

                if (data.length < 2) {
                    continue;
                }

                char character = data[0].charAt(0);

                if (data[1].trim().equals("null")) {
                    mappings.put(character, null);
                    continue;
                }

                String[] particleData = data[1].trim().split("\\|");

                String particleType = particleData[0];
                int rgb = Integer.parseInt(particleData[1]);

                Color color = new Color(rgb);

                Particle particle = new Particle(ParticleType.valueOf(particleType), color);

                mappings.put(character, particle);
            }

            if (isPixels) {
                String[] data = line.trim().split(":");

                if (data.length < 2) {
                    continue;
                }

                int y = Integer.parseInt(data[0]);
                String[] characters = data[1].trim().split("");

                for (int x = 0; x < characters.length; ++x) {
                    Particle particle = mappings.get(characters[x].charAt(0));
                    pixels[y][x] = particle;
                }
            }
        }

        scanner.close();
    }

    private static String serializeParticle(Particle particle) {
        if (particle == null) {
            return null;
        }

        return particle.getType().name() + "|" + particle.getColor().getRGB();
    }
}
