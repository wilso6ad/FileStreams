import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class RandProductMaker extends JFrame {
    private JLabel nameLabel, descriptionLabel, idLabel, costLabel, recordCountLabel;
    private JTextField nameField, descriptionField, idField, costField, recordCountField;
    private JButton addButton;
    private RandomAccessFile randomAccessFile;

    public RandProductMaker() {
        setTitle("The Ultimate Random Product Maker");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 2));

        nameLabel = new JLabel("Name:");
        nameField = new JTextField(35);
        descriptionLabel = new JLabel("Description:");
        descriptionField = new JTextField(75);
        idLabel = new JLabel("ID:");
        idField = new JTextField(6);
        costLabel = new JLabel("Cost:");
        costField = new JTextField(10);
        recordCountLabel = new JLabel("Record Count:");
        recordCountField = new JTextField(10);
        recordCountField.setEditable(false);

        addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addProduct();
            }
        });

        add(nameLabel);
        add(nameField);
        add(descriptionLabel);
        add(descriptionField);
        add(idLabel);
        add(idField);
        add(costLabel);
        add(costField);
        add(recordCountLabel);
        add(recordCountField);
        add(addButton);

        try {
            randomAccessFile = new RandomAccessFile("products.dat", "rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void addProduct() {
        String name = padField(nameField.getText(), 35);
        String description = padField(descriptionField.getText(), 75);
        String id = padField(idField.getText(), 6);
        double cost = Double.parseDouble(costField.getText());

        try {
            randomAccessFile.seek(randomAccessFile.length());
            randomAccessFile.writeUTF(name);
            randomAccessFile.writeUTF(description);
            randomAccessFile.writeUTF(id);
            randomAccessFile.writeDouble(cost);

            nameField.setText("");
            descriptionField.setText("");
            idField.setText("");
            costField.setText("");

            updateRecordCount();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String padField(String field, int length) {
        if (field.length() > length) {
            return field.substring(0, length);
        } else {
            StringBuilder paddedField = new StringBuilder(field);
            while (paddedField.length() < length) {
                paddedField.append(" ");
            }
            return paddedField.toString();
        }
    }

    private void updateRecordCount() throws IOException {
        long recordCount = randomAccessFile.length() / (35 * 2 + 75 * 2 + 6 * 2 + 8);
        recordCountField.setText(String.valueOf(recordCount));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RandProductMaker().setVisible(true);
            }
        });
    }
}
