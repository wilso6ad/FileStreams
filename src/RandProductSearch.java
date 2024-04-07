import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class RandProductSearch extends JFrame {
    private JLabel searchLabel, resultLabel;
    private JTextField searchField;
    private JTextArea resultArea;
    private JButton searchButton;
    private RandomAccessFile randomAccessFile;

    public RandProductSearch() {
        setTitle("The Ultimate Random Product Search");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        searchLabel = new JLabel("Search:");
        searchField = new JTextField(20);
        searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchProducts();
            }
        });

        JPanel searchPanel = new JPanel();
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        resultLabel = new JLabel("Search Results:");
        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        add(searchPanel, BorderLayout.NORTH);
        add(resultLabel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        try {
            randomAccessFile = new RandomAccessFile("Products.dat", "r");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void searchProducts() {
        String searchTerm = searchField.getText();
        resultArea.setText("");
        try {
            randomAccessFile.seek(0);
            while (randomAccessFile.getFilePointer() < randomAccessFile.length()) {
                String name = randomAccessFile.readUTF().trim();
                String description = randomAccessFile.readUTF().trim();
                String id = randomAccessFile.readUTF().trim();
                double cost = randomAccessFile.readDouble();

                if (name.contains(searchTerm)) {
                    resultArea.append("Name: " + name + "\n");
                    resultArea.append("Description: " + description + "\n");
                    resultArea.append("ID: " + id + "\n");
                    resultArea.append("Cost: $" + cost + "\n\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RandProductSearch().setVisible(true);
            }
        });
    }
}
