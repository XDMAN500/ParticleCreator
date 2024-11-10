package dev.fumaz.particlecreator.template;

import dev.fumaz.particlecreator.gui.Project;
import dev.fumaz.particlecreator.util.ExportsPhoenix;

import java.io.IOException;

public class TextGG extends Template {


    @Override
    public Project load() {
        try {
            return ExportsPhoenix.load(this.getClass().getClassLoader().getResourceAsStream("particles/text_gg.yml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
