package dev.fumaz.particlecreator.particle;

public class ParticleOrientation {

    private double offsetPositionX;
    private double offsetPositionY;
    private double offsetPositionZ;
    private double drawRotationAboutX;
    private double drawRotationAboutY;
    private double drawRotationAboutZ;
    private double particleGap;
    private double canvasCenterX;
    private double canvasCenterY;

    public ParticleOrientation(double offsetPositionX, double offsetPositionY, double offsetPositionZ,
                               double drawRotationAboutX, double drawRotationAboutY, double drawRotationAboutZ,
                               double particleGap, double canvasCenterX, double canvasCenterY) {

        this.offsetPositionX = offsetPositionX;
        this.offsetPositionY = offsetPositionY;
        this.offsetPositionZ = offsetPositionZ;
        this.drawRotationAboutX = drawRotationAboutX;
        this.drawRotationAboutY = drawRotationAboutY;
        this.drawRotationAboutZ = drawRotationAboutZ;
        this.canvasCenterX = canvasCenterX;
        this.canvasCenterY = canvasCenterY;
        this.particleGap = particleGap;
    }


    public double getCanvasCenterX() {
        return canvasCenterX;
    }

    public double getCanvasCenterY() {
        return canvasCenterY;
    }

    public double getParticleGap() {
        return particleGap;
    }

    public double getOffsetPositionX() {
        return offsetPositionX;
    }

    public double getOffsetPositionY() {
        return offsetPositionY;
    }

    public double getOffsetPositionZ() {
        return offsetPositionZ;
    }

    public double getDrawRotationAboutX() {
        return drawRotationAboutX;
    }

    public double getDrawRotationAboutY() {
        return drawRotationAboutY;
    }

    public double getDrawRotationAboutZ() {
        return drawRotationAboutZ;
    }

    public void setOffsetPositionX(double offsetPositionX) {
        this.offsetPositionX = offsetPositionX;
    }

    public void setOffsetPositionY(double offsetPositionY) {
        this.offsetPositionY = offsetPositionY;
    }

    public void setOffsetPositionZ(double offsetPositionZ) {
        this.offsetPositionZ = offsetPositionZ;
    }

    public void setDrawRotationAboutX(double drawRotationAboutX) {
        this.drawRotationAboutX = drawRotationAboutX;
    }

    public void setDrawRotationAboutY(double drawRotationAboutY) {
        this.drawRotationAboutY = drawRotationAboutY;
    }

    public void setDrawRotationAboutZ(double drawRotationAboutZ) {
        this.drawRotationAboutZ = drawRotationAboutZ;
    }

    public void setParticleGap(double particleGap) {
        this.particleGap = particleGap;
    }

    public void setCanvasCenterX(double canvasCenterX) {
        this.canvasCenterX = canvasCenterX;
    }

    public void setCanvasCenterY(double canvasCenterY) {
        this.canvasCenterY = canvasCenterY;
    }


    /* public static ParticleOrientation fromConfiguration(ConfigurationSection orientationSection) {
        return new ParticleOrientation(
            orientationSection.getDouble("offsetPositionX"),
            orientationSection.getDouble("offsetPositionY"), orientationSection.getDouble("offsetPositionZ"),
            orientationSection.getDouble("drawRotationAboutX"),
            orientationSection.getDouble("drawRotationAboutY"),
            orientationSection.getDouble("drawRotationAboutZ"),
            orientationSection.getDouble("particleGap"),
            orientationSection.getDouble("canvasCenterX"),
            orientationSection.getDouble("canvasCenterY")
        );
    }*/
}
