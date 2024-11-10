package dev.fumaz.particlecreator.util;

import dev.fumaz.particlecreator.gui.Project;
import dev.fumaz.particlecreator.particle.Particle;
import dev.fumaz.particlecreator.particle.ParticleCanvas;
import dev.fumaz.particlecreator.particle.ParticleOrientation;
import dev.fumaz.particlecreator.particle.ParticleType;

import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ExportsPhoenix {

    private final static String CHARSET = "ABCDEFGHIJKLMNPQRSTUVWXYZabcdefghijklmnpqrstuvwxyz0123456789~-=+<>.,{};' : `";

    public static Project load(File file) throws IOException {
        return load(new FileInputStream(file));
    }

    public static Project load(InputStream inputStream) throws IOException {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(inputStream));
            Project project = new Project();

            ParticleOrientation particleOrientation = new ParticleOrientation(0, 0, 0, 0, 0, 0, 0, 0, 0);
            String line;
            boolean orientation = false;
            boolean pallete = false;
            boolean canvas = false;
            Map<Character,Particle> particleMap = new HashMap<>();
            List<char[]> canvasMatrix = new ArrayList<>();

            do {
                line = reader.readLine();
                if (line == null)
                    break;

                if (line.startsWith("orientation:")) {
                    orientation = true;
                    pallete = false;
                    canvas = false;
                } else if (line.startsWith("palette:")) {
                    pallete = true;
                    orientation = false;
                    canvas = false;
                } else if (line.startsWith("canvas:")) {
                    pallete = false;
                    orientation = false;
                    canvas = true;
                } else if (line.startsWith("  ")) {
                    if (orientation) {
                        String[] parts = line.split(":", 2);
                        String fieldName = parts[0].trim();
                        String fieldValue = parts[1].trim();
                        int commentIndex = fieldValue.indexOf('#');
                        if (commentIndex >=0) {
                            fieldValue = fieldValue.substring(0, commentIndex).trim();
                        }

                        if (fieldName.equalsIgnoreCase("offsetPositionX")) {
                            particleOrientation.setOffsetPositionX(Double.parseDouble(fieldValue));
                        }

                        if (fieldName.equalsIgnoreCase("offsetPositionY")) {
                            particleOrientation.setOffsetPositionY(Double.parseDouble(fieldValue));
                        }

                        if (fieldName.equalsIgnoreCase("offsetPositionZ")) {
                            particleOrientation.setOffsetPositionZ(Double.parseDouble(fieldValue));
                        }

                        if (fieldName.equalsIgnoreCase("drawRotationAboutX")) {
                            particleOrientation.setDrawRotationAboutX(Double.parseDouble(fieldValue));
                        }

                        if (fieldName.equalsIgnoreCase("drawRotationAboutY")) {
                            particleOrientation.setDrawRotationAboutY(Double.parseDouble(fieldValue));
                        }

                        if (fieldName.equalsIgnoreCase("drawRotationAboutZ")) {
                            particleOrientation.setDrawRotationAboutZ(Double.parseDouble(fieldValue));
                        }

                        if (fieldName.equalsIgnoreCase("canvasCenterX")) {
                            particleOrientation.setCanvasCenterX(Double.parseDouble(fieldValue));
                        }

                        if (fieldName.equalsIgnoreCase("canvasCenterY")) {
                            particleOrientation.setCanvasCenterY(Double.parseDouble(fieldValue));
                        }

                        if (fieldName.equalsIgnoreCase("particleGap")) {
                            particleOrientation.setParticleGap(Double.parseDouble(fieldValue));
                        }
                    } else if (pallete) {
                        String[] parts = line.split(":", 2);
                        parts[0] = parts[0].trim();
                        parts[1] = parts[1].trim();
                        String[] particleData = parts[1].split("\\|");

                        String particleType = particleData[0];
                        String colorData = particleData[1];
                        char particleSymbol = parts[0].charAt(0);
                        Particle particle = new Particle(ParticleType.valueOf(particleType.toUpperCase(Locale.ROOT)),
                                                         new Color(Integer.parseInt(colorData.substring(1), 16)));
                        particleMap.put(particleSymbol, particle);
                    } else if (canvas) {
                        int contentIndex = line.indexOf("- ");
                        String content = line.substring(contentIndex + 2);
                        canvasMatrix.add(content.toCharArray());
                    }
                } else {
                    pallete = false;
                    orientation = false;
                    canvas = false;
                }

            } while (line != null);
            ParticleCanvas particleCanvas = new ParticleCanvas(canvasMatrix.get(0).length, canvasMatrix.size());
            for (int y = 0; y < canvasMatrix.size(); y += 1) {
                char[] arr = canvasMatrix.get(y);

                for (int x = 0; x < arr.length; x += 1) {
                    char particleChar = arr[x];
                    Particle particle = particleMap.get(particleChar);
                    if (particle != null) {
                        particleCanvas.setSymbol(x, y, particle);
                    }
                }
            }

            project.setParticleOrientation(particleOrientation);
            project.setParticleCanvas(particleCanvas);
            return project;
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    public static void save(Project project, File file) throws IOException {
        save(project, new FileOutputStream(file));
    }
    public static void save(Project project, OutputStream streamm) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(streamm));

        writer.append("# Phoenix Particle Creator\n");
        writer.append("# Made with <3 by Fumaz and Varmetek\n\n");
        writer.append("version: 1.0\n");
        writer.append("orientation: \n");
        writer.append("  offsetPositionX: %.1f\n".formatted(project.getParticleOrientation().getOffsetPositionX()));
        writer.append("  offsetPositionY: %.1f\n".formatted(project.getParticleOrientation().getOffsetPositionY()));
        writer.append("  offsetPositionZ: %.1f\n".formatted(project.getParticleOrientation().getOffsetPositionZ()));
        writer.append("  drawRotationAboutX: %.1f\n".formatted(project.getParticleOrientation().getDrawRotationAboutX()));
        writer.append("  drawRotationAboutY: %.1f\n".formatted(project.getParticleOrientation().getDrawRotationAboutY()));
        writer.append("  drawRotationAboutZ: %.1f\n".formatted(project.getParticleOrientation().getDrawRotationAboutZ()));
        writer.append("  canvasCenterX: %.1f\n".formatted(project.getParticleOrientation().getCanvasCenterX()));
        writer.append("  canvasCenterY: %.1f\n".formatted(project.getParticleOrientation().getCanvasCenterY()));
        writer.append("  particleGap: %.1f\n".formatted(project.getParticleOrientation().getParticleGap()));
        writer.append("\n\n");
        Map<Particle, Character> characterMap = new LinkedHashMap<>();
        StringBuilder[] canvasBuilder = new StringBuilder[project.getParticleCanvas().getHeight()];

        int symbolSequence = 0;
        for (int y = 0; y < project.getParticleCanvas().getHeight(); y +=1) {
            canvasBuilder[y] = new StringBuilder();
            for (int x = 0; x < project.getParticleCanvas().getWidth(); x +=1) {
                Particle particle = project.getParticleCanvas().getSymbol(x, y);
                char symbol;
                if (particle == null) {
                    symbol = 'O';
                } else if (characterMap.containsKey(particle)) {
                    symbol = characterMap.get(particle);
                } else {
                    symbol = CHARSET.charAt(symbolSequence++);
                    characterMap.put(particle, symbol);
                }
                canvasBuilder[y].append(symbol);
            }
        }

        writer.append("palette: \n");
        for (Map.Entry<Particle, Character> entry : characterMap.entrySet()) {
            String particleData = "%s|#%06X".formatted(entry.getKey().getType().getIdentifier(), entry.getKey().getColor().getRGB() & 0xFFFFFF);
            writer.append("  %s: %s\n".formatted(String.valueOf(entry.getValue()), particleData));
        }
        writer.append("canvas: \n");
        for (StringBuilder builder : canvasBuilder) {
            writer.append("  - " + builder.toString() + " \n");

        }

        writer.flush();
    }
}
