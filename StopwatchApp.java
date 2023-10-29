import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StopwatchApp {
    private static JFrame frame;
    private static JButton startButton;
    private static JButton stopButton;
    private static JButton lapButton;
    private static JTextArea displayArea;
    private static Timer timer;
    private static long startTime;
    private static long elapsedTime;
    private static boolean isRunning;
    private static int lapCount; // Track the lap count

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    private static void createAndShowGUI() {
        frame = new JFrame("Stopwatch");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        displayArea = new JTextArea(2, 20);
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 20));
        frame.add(new JScrollPane(displayArea), BorderLayout.CENTER); // Use a JScrollPane for text area

        JPanel buttonPanel = new JPanel();

        startButton = new JButton("Start");
        startButton.addActionListener(new StartButtonListener());
        buttonPanel.add(startButton);

        stopButton = new JButton("Stop");
        stopButton.addActionListener(new StopButtonListener());
        stopButton.setEnabled(false);
        buttonPanel.add(stopButton);

        lapButton = new JButton("Lap");
        lapButton.addActionListener(new LapButtonListener());
        lapButton.setEnabled(false);
        buttonPanel.add(lapButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);

        timer = new Timer(100, new TimerListener());
        elapsedTime = 0;
        isRunning = false;
        lapCount = 1; // Initialize lap count
    }

    private static class StartButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            startTime = System.currentTimeMillis() - elapsedTime;
            timer.start();
            startButton.setEnabled(false);
            stopButton.setEnabled(true);
            lapButton.setEnabled(true);
            lapCount = 1; // Reset lap count
            isRunning = true;
        }
    }

    private static class StopButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            timer.stop();
            startButton.setEnabled(true);
            stopButton.setEnabled(false);
            lapButton.setEnabled(false);
            isRunning = false;
        }
    }

    private static class LapButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            long lapTime = System.currentTimeMillis() - startTime;
            displayArea.append("Lap " + lapCount + ": " + formatTime(lapTime) + "\n");
            lapCount++; // Increment lap count
        }
    }

    private static class TimerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            elapsedTime = System.currentTimeMillis() - startTime;
            displayArea.setText(formatTime(elapsedTime));
        }
    }

    private static String formatTime(long time) {
        long milliseconds = time % 1000;
        long seconds = (time / 1000) % 60;
        long minutes = (time / (1000 * 60)) % 60;
        long hours = (time / (1000 * 60 * 60)) % 24;
        return String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, milliseconds);
    }
}
