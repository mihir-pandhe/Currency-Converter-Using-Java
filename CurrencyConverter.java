import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class CurrencyConverter extends JFrame {
    private JComboBox<String> fromCurrency;
    private JComboBox<String> toCurrency;
    private JTextField amountField;
    private JLabel resultLabel;
    private JButton convertButton;

    private HashMap<String, Double> conversionRates;

    public CurrencyConverter() {
        setTitle("Currency Converter");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        fromCurrency = new JComboBox<>(new String[]{"USD", "EUR", "GBP"});
        toCurrency = new JComboBox<>(new String[]{"USD", "EUR", "GBP"});
        amountField = new JTextField(10);
        resultLabel = new JLabel("Converted amount: ");
        convertButton = new JButton("Convert");

        conversionRates = new HashMap<>();
        conversionRates.put("USDEUR", 0.92);
        conversionRates.put("USDGBP", 0.75);
        conversionRates.put("EURUSD", 1.18);
        conversionRates.put("EURGBP", 0.88);
        conversionRates.put("GBPUSD", 1.33);
        conversionRates.put("GBPEUR", 1.14);

        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String from = fromCurrency.getSelectedItem().toString();
                String to = toCurrency.getSelectedItem().toString();
                double amount = Double.parseDouble(amountField.getText());
                double rate = conversionRates.getOrDefault(from + to, 1.0);
                double convertedAmount = amount * rate;
                resultLabel.setText("Converted amount: " + convertedAmount);
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

    public static void main(String[] args) {
        new CurrencyConverter();
    }
}
