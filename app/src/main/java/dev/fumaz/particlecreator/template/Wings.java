package dev.fumaz.particlecreator.template;

public class Wings extends Template {

    private final boolean o = false;
    private final boolean X = true;

    @Override
    public boolean[][] getPixels() {
        return new boolean[][]{
                { o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o },
                { o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o },
                { o, o, o, X, X, X, o, o, o, o, o, o, X, X, X, o, o, o, o },
                { o, o, o, X, X, X, X, o, o, o, o, X, X, X, X, o, o, o, o },
                { o, o, o, X, X, X, X, X, o, o, X, X, X, X, X, o, o, o, o },
                { o, o, o, o, X, X, X, X, o, o, X, X, X, X, o, o, o, o, o },
                { o, o, o, o, o, X, X, X, X, X, X, X, X, o, o, o, o, o, o },
                { o, o, o, o, o, o, X, X, X, X, X, X, o, o, o, o, o, o, o },
                { o, o, o, o, o, o, o, X, X, X, X, o, o, o, o, o, o, o, o },
                { o, o, o, o, o, o, o, o, X, X, o, o, o, o, o, o, o, o, o },
                { o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o },
                { o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o },
                { o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o } };
    }

}
