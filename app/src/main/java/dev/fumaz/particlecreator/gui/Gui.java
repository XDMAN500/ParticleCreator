package dev.fumaz.particlecreator.gui;

import dev.fumaz.particlecreator.Coordinate;
import dev.fumaz.particlecreator.particle.Particle;
import dev.fumaz.particlecreator.particle.ParticleCanvas;
import dev.fumaz.particlecreator.particle.ParticleOrientation;
import dev.fumaz.particlecreator.particle.ParticleType;
import dev.fumaz.particlecreator.template.Cape;
import dev.fumaz.particlecreator.template.Crown;
import dev.fumaz.particlecreator.template.Template;
import dev.fumaz.particlecreator.util.ExportsPhoenix;
import dev.fumaz.particlecreator.util.SizedStack;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class Gui {

    public final static double VERSION = 1.1;

    private final static int WIDTH = 19;
    private final static int HEIGHT = 13;
    private final static int PIXEL_SIZE = 30;

    private Project project = new Project();
    private final List<Template> templates = Arrays.asList(new Cape(), new Crown());
    private final SizedStack<Action> undo = new SizedStack<>(250);
    private final SizedStack<Action> redo = new SizedStack<>(250);
    private final JColorChooser colorChooser = new JColorChooser(Color.BLACK);

    private Particle particle = new Particle(ParticleType.DUST, Color.BLACK);
    private Color background = Color.WHITE;
    private boolean running = true;

    public void show() {
        project.setParticleCanvas(new ParticleCanvas(WIDTH, HEIGHT));
        project.setParticleOrientation(new ParticleOrientation(
            0.0,
            2.2,
            0.0,
            90,
            0,
            0,
            8,
            6,
            0.2
        ));
        JFrame frame = new JFrame("Particle Creator");
        frame.setSize(WIDTH * PIXEL_SIZE+ PIXEL_SIZE, HEIGHT * PIXEL_SIZE + 80);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setFocusable(true);

        JMenuBar jMenuBar = new JMenuBar();


        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(frame.getWidth()  - PIXEL_SIZE , frame.getHeight() - 80 ));

        JPanel buttons = new JPanel();
        buttons.setPreferredSize(new Dimension(frame.getWidth(), 50));

        GridLayout layout = new GridLayout(1, 5);
        buttons.setLayout(layout);

        JButton particleButton = new JButton("Particle");
        particleButton.addActionListener(e -> {
            ParticleType type = (ParticleType) JOptionPane.showInputDialog(frame, "Please note only REDSTONE particle can have colors.\nColors selected for other particles are purely aesthetic.", "Choose a particle", JOptionPane.INFORMATION_MESSAGE, null, ParticleType.values(), particle.getType());

            if (type == null) {
                return;
            }

            Color color = showColorPicker(frame);

            particle = new Particle(type, color);
        });

        JButton colorButton = new JButton("Color");
        colorButton.addActionListener(e -> {
            particle = new Particle(ParticleType.DUST, showColorPicker(frame));
        });
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            chooser.setDialogTitle("Save File");
            chooser.setDialogType(JFileChooser.SAVE_DIALOG);
            chooser.setFileHidingEnabled(true);
            chooser.setFileFilter(new FileNameExtensionFilter("YAML File", "yml"));

            int option = chooser.showSaveDialog(frame);

            if (option != JFileChooser.APPROVE_OPTION) {
                return;
            }

            File file = chooser.getSelectedFile();

            if (!file.getName().endsWith(".yml")) {
                file = new File(file.getAbsolutePath() + ".yml");
            }

            try {
                ExportsPhoenix.save(Gui.this.project, file);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "An error occurred while saving the file!\nPlease send this to fumaz:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton openButton = new JButton("Open");
        openButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            chooser.setDialogTitle("Open File");
            chooser.setDialogType(JFileChooser.OPEN_DIALOG);
            chooser.setFileHidingEnabled(true);
            chooser.setFileFilter(new FileNameExtensionFilter("YAML File", "yml"));

            int option = chooser.showOpenDialog(frame);

            if (option != JFileChooser.APPROVE_OPTION) {
                return;
            }

            File file = chooser.getSelectedFile();

            try {

                ParticleCanvas particleCanvas = new ParticleCanvas(WIDTH, HEIGHT);
                Project newProject = ExportsPhoenix.load(file);
                particleCanvas.copyFrom(newProject.getParticleCanvas());
                newProject.setParticleCanvas(particleCanvas);
                Gui.this.project = newProject;
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "An error occurred while opening the file!\nPlease send this to fumaz:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton templateButton = new JButton("Template");
        templateButton.addActionListener(e -> {
            Template template = (Template) JOptionPane.showInputDialog(frame, null, "Choose Template", JOptionPane.INFORMATION_MESSAGE, null, templates.toArray(), templates.get(0));

            if (template == null) {
                return;
            }

            ParticleCanvas normalizedCanvas = new ParticleCanvas(WIDTH, HEIGHT);
            Project newProject = template.load();
            normalizedCanvas.copyFrom(newProject.particleCanvas);
            newProject.setParticleCanvas(normalizedCanvas);
            Gui.this.project = newProject;

        });

        JFrame dialogFrame = new JFrame();
        JDialog dialogBox = new JDialog(dialogFrame, "Dialog Example", true);
        dialogBox.setSize(new Dimension(300, 300));

        JPanel dialogPanel = new JPanel();
        dialogPanel.setLayout(new BoxLayout(dialogPanel, BoxLayout.Y_AXIS));

        Dictionary<Integer, JLabel> labelTable =  new Hashtable<>(Map.of(10, new JLabel("10"), 40, new JLabel("40")));
        JLabel widthLabel = new JLabel("Width");
        JLabel heightLabel = new JLabel("height");
        JSlider widthSlider = new JSlider(10,40);
        widthSlider.setLabelTable(labelTable);
        JSlider heightSlider = new JSlider(10,40);
        heightSlider.addChangeListener( e -> {
            heightLabel.setText("Height %d".formatted(heightSlider.getValue()));
            heightLabel.updateUI();
        });
        widthSlider.addChangeListener( e -> {
            widthLabel.setText("Width %d".formatted(widthSlider.getValue()));
            widthLabel.updateUI();
        });
        heightSlider.setLabelTable(labelTable);
        dialogPanel.add(widthLabel);
        dialogPanel.add(widthSlider);
        dialogPanel.add(heightLabel);
        dialogPanel.add(heightSlider);
        dialogBox.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                clear();
                frame.setSize(widthSlider.getValue() * PIXEL_SIZE+ PIXEL_SIZE, heightSlider.getValue() * PIXEL_SIZE + 80);
                frame.setSize(widthSlider.getValue() * PIXEL_SIZE+ PIXEL_SIZE, heightSlider.getValue() * PIXEL_SIZE + 80);
                panel.setSize(new Dimension(frame.getWidth()  - PIXEL_SIZE , frame.getHeight() - 80 ));
                //setLabel("Thwarted user attempt to close window.");
            }
        });
        dialogBox.add(dialogPanel);



        /*JButton settingButton = new JButton("Setting");
        settingButton.addActionListener(e -> {
            dialogBox.setVisible(true);
        });*/

        jMenuBar.add(openButton);
        jMenuBar.add(saveButton);
        jMenuBar.add(particleButton);
        jMenuBar.add(colorButton);
        jMenuBar.add(templateButton);
        //jMenuBar.add(settingButton);
        frame.setJMenuBar(jMenuBar);

        //frame.add(buttons, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.SOUTH);
        frame.setVisible(true);

        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Particle p = particle;

                if (SwingUtilities.isRightMouseButton(e)) {
                    p = null;
                }

                int x = e.getX();
                int y = e.getY();

                int pixelX = x / PIXEL_SIZE;
                int pixelY = y / PIXEL_SIZE;

                if (pixelX < 0 || pixelY < 0 || pixelX >= WIDTH || pixelY >= HEIGHT) {
                    return;
                }

                if (project.getParticleCanvas().getSymbol(pixelX, pixelY) == null) {
                    if (p == null) {
                        return;
                    }
                } else if (project.getParticleCanvas().getSymbol(pixelX, pixelY).equals(p)) {
                    return;
                }

                undo.push(new Action(new Coordinate(pixelX, pixelY), project.getParticleCanvas().getSymbol(pixelX, pixelY), p));
                project.getParticleCanvas().setSymbol(pixelX, pixelY, p);
            }
        });

        panel.setFocusable(true);
        panel.setRequestFocusEnabled(true);
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK), "clear");
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_H, KeyEvent.CTRL_DOWN_MASK), "help");
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_L, KeyEvent.CTRL_DOWN_MASK), "legend");
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK), "undo");
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK | KeyEvent.SHIFT_DOWN_MASK), "redo");
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_B, KeyEvent.CTRL_DOWN_MASK), "background");
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_J, KeyEvent.CTRL_DOWN_MASK), "pick");

        panel.getActionMap().put("clear", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
            }
        });

        panel.getActionMap().put("help", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Particle Creator " + VERSION + "\n" +
                        "Made with <3 by Fumaz\n\n" +
                        "CTRL + H - Shows this message\n" +
                        "CTRL + C - Clears the board\n" +
                        "CTRL + B - Change background color\n" +
                        "CTRL + J - Pick color under cursor\n" +
                        "CTRL + Z - Undo your last action\n" +
                        "CTRL + Shift + Z - Redo your last action\n" +
                        "Left Click - Colors a pixel\n" +
                        "Right Click - Erases a pixel", "Help", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        panel.getActionMap().put("legend", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "TODO", "TODO", JOptionPane.WARNING_MESSAGE);
            }
        });

        panel.getActionMap().put("undo", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (undo.isEmpty()) {
                    return;
                }

                Action action = undo.pop();
                action.undo(Gui.this.project);
                redo.push(action);
            }
        });

        panel.getActionMap().put("redo", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (redo.isEmpty()) {
                    return;
                }

                Action action = redo.pop();
                action.redo(Gui.this.project);
                undo.push(action);
            }
        });

        panel.getActionMap().put("background", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color color = JColorChooser.showDialog(frame, "Choose a background color", background);

                if (color == null) {
                    return;
                }

                background = color;
            }
        });

        panel.getActionMap().put("pick", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Point point = MouseInfo.getPointerInfo().getLocation();

                int x = point.x - frame.getLocation().x;
                int y = point.y - frame.getLocation().y;

                int pixelX = x / PIXEL_SIZE;
                int pixelY = (y - 80) / PIXEL_SIZE;

                if (pixelX < 0 || pixelY < 0 || pixelX >= WIDTH || pixelY >= HEIGHT) {
                    return;
                }

                Particle particle = project.getParticleCanvas().getSymbol(pixelX, pixelY);

                if (particle == null) {
                    return;
                }

                Gui.this.particle = particle;
            }
        });

        clear();
        panel.requestFocus();

        new Thread(() -> {
            while (running && frame.isVisible()) {
                update(panel.getGraphics());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void update(Graphics graphics) {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                Particle particle = project.getParticleCanvas().getSymbol(x, y);
                Color color = particle == null ? null : particle.getColor();
                boolean empty = particle == null;

                if (empty) {
                    color = background;
                }

                graphics.setColor(color);
                graphics.fillRect(x * PIXEL_SIZE, y * PIXEL_SIZE, PIXEL_SIZE, PIXEL_SIZE);
                graphics.setColor(color == Color.black ? Color.white : Color.black);
                graphics.drawRect(x * PIXEL_SIZE, y * PIXEL_SIZE, PIXEL_SIZE, PIXEL_SIZE);
            }
        }
    }

    private void clear() {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                project.getParticleCanvas().setSymbol(x,y, null);
            }
        }
    }


    private Color showColorPicker(JFrame frame) {
        colorChooser.setColor(particle.getColor());
        JDialog dialog = JColorChooser.createDialog(frame, "Choose a color", true, colorChooser, null, null);
        dialog.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentHidden(ComponentEvent e) {
                ((Window) e.getComponent()).dispose();
            }
        });

        dialog.show();

        return colorChooser.getColor();
    }

}
