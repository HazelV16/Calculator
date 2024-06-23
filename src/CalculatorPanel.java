import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The calculator panel
 *
 * @author Carl Mooney -- change to your name
 */
public class CalculatorPanel extends JPanel implements ButtonConstants {
    private final int CALC_WIDTH = 265;
    private final int CALC_HEIGHT = 375;
    private JLabel result;
    private JLabel calculation;
    private JButton[] buttons = new JButton[29]; //array that stores number buttons
    String percentageOperator;

    private JPanel memoryPanel;
    private JPanel calcPanel;
    private double num1 = 0.0;
    private double num2 = 0.0;
    private String op;
    private boolean startingNumber = true;
    double percentageValue;
    String storing_label = "";
    double answer;
    private String[] buttonTexts = {
            ButtonConstants.percentage, ButtonConstants.CE, ButtonConstants.C,
            ButtonConstants.DELETE, ButtonConstants.RECIPROCAL, ButtonConstants.X_SQUARED,
            ButtonConstants.SQUARE_ROOT, ButtonConstants.DIVISION, "7", "8", "9",
            ButtonConstants.MULTIPLICATION, "4", "5", "6", ButtonConstants.SUBTRACTION,
            "1", "2", "3", ButtonConstants.ADDITION, ButtonConstants.CHANGE_SIGN, "0", ButtonConstants.DECIMAL, ButtonConstants.EQUALS,
            ButtonConstants.memoryMC, ButtonConstants.memoryMR, ButtonConstants.memoryMplus,
            ButtonConstants.memoryMminus, ButtonConstants.menoryMS};
    private double memory = 0.0;


    /**
     * Constructor for the Calculator Panel: Sets up the GUI
     */
    public CalculatorPanel() {
        init();
        this.add(memoryPanel); // Add memoryPanel to the CalculatorPanel
        this.add(calcPanel);
    }

    private void init() {
        this.setBackground(Color.LIGHT_GRAY);
        this.setPreferredSize(new Dimension(CALC_WIDTH, CALC_HEIGHT));
        result = new JLabel("0", JLabel.RIGHT);
        result.setPreferredSize(new Dimension(CALC_WIDTH - 5, 50));
        result.setFont(new Font("Arial", Font.BOLD, 32));
        result.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        result.setOpaque(true); // Set JLabel to be opaque
        result.setBackground(Color.WHITE); // Set the background color to white
//
        calculation = new JLabel("", JLabel.RIGHT);
        calculation.setFont(new Font("Helvetica", Font.PLAIN, 12));
        calculation.setBackground(Color.LIGHT_GRAY);
        calculation.setPreferredSize(new Dimension(CALC_WIDTH - 5, 20));

        this.add(calculation);
        this.add(result);

        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton(buttonTexts[i]);
            if (i >= 24 && i <= 28) {
                buttons[i].setPreferredSize(new Dimension(48, 30));
                buttons[i].setFont(new Font("Helvetica", Font.PLAIN, 12));
                buttons[i].setBackground(Color.LIGHT_GRAY);
            } else {
                buttons[i].setPreferredSize(new Dimension(65, 40)); // Adjust the size as needed
                if (buttonTexts[i].matches("[0-9]")) {
                    buttons[i].setFont(new Font("Helvetica", Font.BOLD, 13));
                } else {
                    buttons[i].setFont(new Font("Helvetica", Font.PLAIN, 13));
                }
                buttons[i].setForeground(Color.DARK_GRAY);
            }
            if (buttonTexts[i].equals(ButtonConstants.C) || buttonTexts[i].equals(ButtonConstants.CE)) {
                buttons[i].setForeground(Color.RED);
            }

            if (buttonTexts[i].equals(ButtonConstants.EQUALS)) {
                buttons[i].setForeground(new Color(0.13f, 0.55f, 0.13f));
            }

            // Add action listeners
            buttons[i].addActionListener(new ButtonListener());
        }
        memoryPanel = new JPanel();
        memoryPanel.setBackground(Color.LIGHT_GRAY);
        memoryPanel.setLayout(new GridLayout(1, 1, 1, 1));

        calcPanel = new JPanel();
        calcPanel.setBackground(Color.LIGHT_GRAY);
        EmptyBorder border = new EmptyBorder(2, 2, 2, 2);
        calcPanel.setLayout(new GridLayout(7, 4, 1, 1));

        for (int i = 24; i <= 28; i++) {
            memoryPanel.add(buttons[i]);
        }
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 4; col++) {
                int buttonIndex = row * 4 + col; // Calculate the index for the button
                if (buttonIndex < buttons.length) {
                    calcPanel.add(buttons[buttonIndex]);
                }
            }
        }
    }

    public void pressNum(String Number) {
        if (startingNumber) {
            result.setText(Number);
//            storing_label += Number;
//            calculation.setText(storing_label);
            startingNumber = false;
        } else {
            result.setText(result.getText() + Number);
//            calculation.setText(calculation.getText() + " "+ op + " " + calculation.getText());
//            storing_label += Number;
//            calculation.setText(storing_label);

        }

    }

    public void pressOperator(String operator) {
        num1 = Double.parseDouble(result.getText());
        op = operator;
        startingNumber = true;
//        if (op!=null && answer!=0.0) {
//            storing_label+=answer;
//        }
//        if(op!=null){
//            storing_label += op;
//        }

    }

    private double calculate(String op, Double num1, Double num2) {
        switch (op) {
            case (ButtonConstants.MULTIPLICATION):
                num1 *= num2;
                break;
            case (ButtonConstants.DIVISION):
                if (num2 != 0) {
                    num1 /= num2;
                    break;
                } else {
                    result.setText("Error");
                    break;
                }
            case (ButtonConstants.ADDITION):
                num1 += num2;
                break;
            case (ButtonConstants.SUBTRACTION):
                num1 -= num2;
                break;
        }
        return num1; //return the result of the calculation
    }

    public void displayIntOrDouble(double value) {
        // if number is an integer
        if (value == (int) value) result.setText(String.valueOf((int) value));//display integer
            // if decimal number
        result.setText(String.valueOf(value));
    }

    public void pressEqual() {
        if (percentageOperator != null) {
            if (op == ButtonConstants.ADDITION || op == ButtonConstants.SUBTRACTION) {
                // An operator is set, perform the operation with percentage
                answer = calculate(op, num1, num1 * percentageValue);
                displayIntOrDouble(answer);
//                storing_label+=value;
//                num1 = value;
            } else if (op == ButtonConstants.DIVISION || op == ButtonConstants.MULTIPLICATION) {
//                num2 = Double.parseDouble(result.getText());
                System.out.println("here: " + num1 + " " + percentageValue);
                double value = calculate(op, num1, percentageValue);
                displayIntOrDouble(value);
//                num1 = value;
            }

        } else if (op != null) {
            num2 = Double.parseDouble(result.getText());
            answer = calculate(op, num1, num2);
            displayIntOrDouble(answer);
        }
//        storing_label += "=";
//        calculation.setText(storing_label);
//        op = null;
        startingNumber = true;
    }

    public void pressClear() {
        num1 = 0.0;
        num2 = 0.0;
        result.setText("0"); //set the current text to 0
        startingNumber = true;
        calculation.setText("");
        storing_label = "";
    }

    public void pressDelete() {
        String currentText = result.getText();
        if (!currentText.equals("0")) {
            if (currentText.length() == 1) {
                result.setText("0");
            } else {
                result.setText(currentText.substring(0, currentText.length() - 1));
            }
        }
    }

    public void pressXSquared() {
        double value = Double.parseDouble(result.getText());
        value = value * value;
        displayIntOrDouble(value);
        storing_label += "sqr";
        calculation.setText(storing_label);

    }

    public void pressSquareRoot() {
//        storing_label = "";
        double value = Double.parseDouble(result.getText());
        if (value < 0) {
            result.setText("Error");
        } else {
            value = Math.sqrt(value);
            displayIntOrDouble(value);

        }
//        storing_label+=ButtonConstants.SQUARE_ROOT;
//        storing_label += value;
//        calculation.setText(storing_label);
//        calculation.setText(storing_label);
    }

    public void pressReciprocal() {
        double value = Double.parseDouble(result.getText());
        if (value == 0) {
            result.setText("Error");
        } else {
            value = 1 / value;
            displayIntOrDouble(value);
        }
    }

    public void pressPercentage() {
        double value = Double.parseDouble(result.getText());
        percentageValue = value * 0.01; // Store the percentage value
        percentageOperator = op; // Store the operator
        startingNumber = true; // Allow entering a new number
    }

    void pressMemory(String memoryFunction) {
        switch (memoryFunction) {
            case (ButtonConstants.memoryMplus) ->
                    memory += Double.parseDouble(result.getText()); //add the value to the current value of memory
            case (ButtonConstants.memoryMminus) ->
                    memory -= Double.parseDouble(result.getText()); //subtract the current value of the JLabel from the float variable memory
            case (ButtonConstants.memoryMR) ->
                    result.setText(String.valueOf(memory));//display (based on type) the current value of memory on the JLabel
            case (ButtonConstants.memoryMC) -> memory = 0.0;//set the value of memory to 0
            case (ButtonConstants.menoryMS) -> memory = Double.parseDouble(result.getText());
        }
    }

    private class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            switch (command) {
                case "1", "2", "3", "4", "5", "6", "7", "8", "9":
                    pressNum(command);
                    calculation.setText(calculation.getText() + command);
                    break;
                case ButtonConstants.ADDITION:
                case ButtonConstants.SUBTRACTION:
                case ButtonConstants.MULTIPLICATION:
                case ButtonConstants.DIVISION:
                    pressOperator(command);
                    calculation.setText(result.getText() + " " + op + " ");
                    break;
                case (ButtonConstants.DELETE):
                    pressDelete();
                    break;
                case (ButtonConstants.C):
                case (ButtonConstants.CE):
                    pressClear();
                    break;
                case (ButtonConstants.SQUARE_ROOT):
                    pressSquareRoot();
                    calculation.setText(calculation.getText() + ButtonConstants.SQUARE_ROOT);
                    break;
                case (ButtonConstants.X_SQUARED):
                    pressXSquared();
                    break;
                case (ButtonConstants.RECIPROCAL):
                    pressReciprocal();
                    break;
                case (ButtonConstants.percentage):
                    pressPercentage();
                    calculation.setText(calculation.getText() + answer);
                    break;
                case (ButtonConstants.EQUALS):
                    pressEqual();
                    calculation.setText(result.getText() + " =");
                    break;
                case (ButtonConstants.memoryMC):
                case (ButtonConstants.memoryMR):
                case (ButtonConstants.memoryMminus):
                case (ButtonConstants.memoryMplus):
                case (ButtonConstants.menoryMS):
                    pressMemory(command);
                    break;
            }
        }
    }
}
