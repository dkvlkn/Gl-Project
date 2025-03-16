package GUI;

public class AlgorithmGUI extends JFrame {
    private static final long serialVersionUID = 1L;

    public AlgorithmGUI(String algorithm) {
        setTitle(algorithm + " Algorithm");
        setSize(600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10));

        JButton startButton = new JButton("START");
        JButton restartButton = new JButton("RESTART");
        JButton backButton = new JButton("BACK");
        JButton stopButton = new JButton("STOP");

        backButton.addActionListener(e -> dispose());
        
        panel.add(startButton);
        panel.add(restartButton);
        panel.add(stopButton);
        panel.add(backButton);

        add(panel, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
