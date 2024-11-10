package dev.fumaz.particlecreator.template;

import dev.fumaz.particlecreator.gui.Project;

public abstract class Template {

    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public String toString() {
        return getName();
    }


    public abstract Project load();

}
