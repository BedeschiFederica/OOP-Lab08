package it.unibo.oop.lab.mvcio2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import it.unibo.oop.lab.mvcio.Controller;

/**
 * A very simple program using a graphical interface.
 * 
 */
public final class SimpleGUIWithFileChooser {

    /*
     * Starting from the application in mvcio:
     * 
     * 1) Add a JTextField and a button "Browse..." on the upper part of the
     * graphical interface.
     * Suggestion: use a second JPanel with a second BorderLayout, put the panel
     * in the North of the main panel, put the text field in the center of the
     * new panel and put the button in the line_end of the new panel.
     * 
     * 2) The JTextField should be non modifiable. And, should display the
     * current selected file.
     * 
     * 3) On press, the button should open a JFileChooser. The program should
     * use the method showSaveDialog() to display the file chooser, and if the
     * result is equal to JFileChooser.APPROVE_OPTION the program should set as
     * new file in the Controller the file chosen. If CANCEL_OPTION is returned,
     * then the program should do nothing. Otherwise, a message dialog should be
     * shown telling the user that an error has occurred (use
     * JOptionPane.showMessageDialog()).
     * 
     * 4) When in the controller a new File is set, also the graphical interface
     * must reflect such change. Suggestion: do not force the controller to
     * update the UI: in this example the UI knows when should be updated, so
     * try to keep things separated.
     */

    private final JFrame frame = new JFrame("My second Java graphical interface");

    /**
     * builds a new {@link SimpleGUI}.
     * @param controller
     *          the controller that controls the I/O access.
     */
    public SimpleGUIWithFileChooser(final Controller controller) {
        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        final int sw = (int) screen.getWidth();
        final int sh = (int) screen.getHeight();
        this.frame.setSize(sw / 2, sh / 2);
        this.frame.setLocationByPlatform(true);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        final JTextArea text = new JTextArea();
        final JButton save = new JButton("Save");
        mainPanel.add(text, BorderLayout.CENTER);
        mainPanel.add(save, BorderLayout.SOUTH);
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                try {
                    controller.write(text.getText());
                } catch (IOException exc) {
                    System.out.println(exc.getMessage());
                }
            }
        });
        final JPanel filePanel = new JPanel();
        filePanel.setLayout(new BorderLayout());
        mainPanel.add(filePanel, BorderLayout.NORTH);
        final JTextField fileField = new JTextField();
        fileField.setEditable(false);
        fileField.setText(controller.getPath());
        final JButton browse = new JButton("Browse...");
        browse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final JFileChooser fileChooser = new JFileChooser();
                fileChooser.setSelectedFile(controller.getCurrentFile());
                final int result = fileChooser.showSaveDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    controller.setCurrentFile(fileChooser.getSelectedFile());
                    fileField.setText(controller.getPath());
                    frame.revalidate();
                } else if (result != JFileChooser.CANCEL_OPTION) {
                    JOptionPane.showMessageDialog(frame, result, "Browse failed", JOptionPane.ERROR_MESSAGE);
                }
            } 
        });
        filePanel.add(fileField, BorderLayout.CENTER);
        filePanel.add(browse, BorderLayout.LINE_END);
        this.frame.setContentPane(mainPanel);
    }

    /**
     * displays the frame.
     */
    public void display() {
        this.frame.setVisible(true);
    }

    /**
     * @param args
     *          ignored
     */
    public static void main(final String... args) {
        final Controller controller = new Controller();
        final SimpleGUIWithFileChooser gui = new SimpleGUIWithFileChooser(controller);
        gui.display();
    }

}
