package dev.fumaz.particlecreator.particle;

public class ParticleCanvas {
    private final Particle[][] charMatrix;
    private final int width;
    private final int height;

    public ParticleCanvas(Particle[][] charMatrix) {
        this.charMatrix = charMatrix;
        this.height = charMatrix.length;
        this.width = charMatrix[0].length;
    }

    public ParticleCanvas(int width, int height) {
        this.charMatrix = new Particle[height][width];
        this.height = charMatrix.length;
        this.width = charMatrix[0].length;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Particle getSymbol(int x, int y) {
        if (0 <= y && y < height) {
            if (0 <= x && x < charMatrix[y].length) {
                return charMatrix[y][x];
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public void setSymbol(int x, int y, Particle particle) {
        if (0 <= y && y < height) {
            if (0 <= x && x < charMatrix[y].length) {
                charMatrix[y][x] = particle;
            }
        }
    }

    public void copyFrom(ParticleCanvas particleCanvas) {
        for (int x = 0; x < Math.min(getWidth(), particleCanvas.getWidth()); x +=1) {
            for (int y = 0; y < Math.min(getHeight(), particleCanvas.getHeight()); y +=1) {
                setSymbol(x, y, particleCanvas.getSymbol(x, y));
            }
        }
    }


}
