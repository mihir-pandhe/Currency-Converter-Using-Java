import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class CurrencyConverter extends JFrame {
    private JComboBox<String> fromCurrency;
    private JComboBox<String> toCurrency;
    private JTextField amountField;
    private JLabel resultLabel;
    private JButton convertButton;

    public CurrencyConverter() {
        setTitle("Currency Converter");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        fromCurrency = new JComboBox<>(new String[]{"USD", "EUR", "GBP"});
        toCurrency = new JComboBox<>(new String[]{"USD", "EUR", "GBP"});
        amountField = new JTextField(10);
        resultLabel = new JLabel("Converted amount: ");
        convertButton = new JButton("Convert");

        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String from = fromCurrency.getSelectedItem().toString();
                    String to = toCurrency.getSelectedItem().toString();
                    double amount = Double.parseDouble(amountField.getText());
                    double rate = fetchConversionRate(from, to);
                    double convertedAmount = amount * rate;
                    resultLabel.setText("Converted amount: " + String.format("%.2f", convertedAmount));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid number.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                }
            }
        });

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(new JLabel("From:"));
        add(fromCurrency);
        add(new JLabel("To:"));
        add(toCurrency);
        add(new JLabel("Amount:"));
        add(amountField);
        add(convertButton);
        add(resultLabel);

        setVisible(true);
    }

    private double fetchConversionRate(String from, String to) throws Exception {
        String urlStr = "https://api.exchangerate-api.com/v4/latest/" + from;
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        conn.disconnect();

        JSONObject json = new JSONObject(content.toString());
        JSONObject rates = json.getJSONObject("rates");
        if (!rates.has(to)) {
            throw new Exception("Currency not available.");
        }
        return rates.getDouble(to);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CurrencyConverter());
    }
}
