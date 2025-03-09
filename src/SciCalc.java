import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

// TODO list ==========================================================
// 4. Add trigonometric functions
// 6. error handling

// TODO list ==========================================================

public class SciCalc extends JFrame implements ActionListener
{
    Calculations calculations = new Calculations();
    String input = "";
    JTextField textField;
    boolean clearNextInput = false;
    boolean secondNumber = false;

    // Strings that use unicode characters for the buttons
    String piString = '\u03C0' + "";
    String divideString = '\u00F7' + "";
    String multiplyString = '\u00D7' + "";
    String arrowString = '\u2190' + "";
    String secondString = "2" + '\u207F' + '\u1D48';
    String firstString = "1" + '\u02E2' + '\u1D57';
    String xSquaredString = "x" + '\u00B2';
    String xCubedString = "x" + '\u00B3';
    String xToYString = "x" + '\u02B8';
    String tenToXString = "10" + '\u02E3';
    String xRootString = '\u00B2' + "√x";
    String xCubeRootString = '\u00B3' + "√x";
    String yRootXString = '\u02B8' + "√x";
    String logBaseXString = "log" + '\u2093';
    String eToXString = "e" + '\u02E3';
    String plusMinusString = '\u00B1' + "";
    String twoToTheX = "2" + '\u02E3';

    // Strings that are shown on the buttons (also known as first buttons
    String[] firstButtonLabels = {
        secondString, piString, "e", "C", arrowString,
        xSquaredString, "1/x", "|x|", "exp", "%",
        xRootString, "(", ")", "n!", divideString,
        xToYString, "7", "8", "9", multiplyString,
        tenToXString, "4", "5", "6", "-",
        "log", "1", "2", "3", "+",
        "ln", plusMinusString, "0", ".", "="
    };

    // Buttons that are only shown when the second button is clicked
    String[] secondButtonLabels = {
        firstString, piString, "e", "C", arrowString,
        xCubedString, "1/x", "|x|", "exp", "%",
        xCubeRootString, "(", ")", "n!", divideString,
        yRootXString, "7", "8", "9", multiplyString,
        twoToTheX, "4", "5", "6", "-",
        logBaseXString, "1", "2", "3", "+",
        eToXString, plusMinusString, "0", ".", "="
    };

    // Create a panel for the buttons to be placed on
    // NOTE: this gets reset when switching between first and second functions
    JPanel buttonsPanel = new JPanel();

    public void run()
    {
        CalculatorVisual();
    }

    private void CalculatorVisual()
    {
        // Create a window
        setTitle("Scientific Calculator");
        setSize(450, 600);
        setLayout(new BorderLayout(10, 10)); // Add padding around the edges
        setResizable(true);
        // Exit the program when the user closes the window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a field to display text for the entire top of the window and start the text on the right side of the box
        textField = new JTextField(12);
        textField.setFont(new Font("Arial", Font.PLAIN, 40));
        textField.setHorizontalAlignment(JTextField.RIGHT);
        textField.setEditable(false);
        add(textField, BorderLayout.NORTH);

        // Add the panel to the window
        add(buttonsPanel, BorderLayout.CENTER);
        // Create the default buttons
        SwitchToFirstFunctions();

        // Add a component listener to handle resizing
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = getWidth();
                int height = getHeight();
                textField.setFont(new Font("Arial", Font.PLAIN, width / 15));
                for (Component comp : buttonsPanel.getComponents()) {
                    if (comp instanceof JButton) {
                        JButton button = (JButton) comp;
                        button.setFont(new Font("Arial", Font.PLAIN, width / 25));
                        button.setPreferredSize(new Dimension(width / 7, height / 10));
                    }
                }
                buttonsPanel.revalidate();
            }
        });

        // Add padding around the edges
        ((JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Make the window visible
        setVisible(true);
    }

    // When a button is clicked
    public void actionPerformed(ActionEvent e)
    {
        // check if the input needs to be cleared and if the input is a number
        if (clearNextInput && !Character.isDigit(e.getActionCommand().charAt(0)))
        {
            // find the last number in the input
            double num = 0;
            int startIndex = -1;
            for (int i = input.length() - 1; i >= 0; i--)
            {
                if (Character.isDigit(input.charAt(i)) || input.charAt(i) == '.')
                {
                    startIndex = i;
                }
                else if (input.charAt(i) == '-' && (i == 0 || !Character.isDigit(input.charAt(i - 1))))
                {
                    startIndex = i;
                    break;
                }
                else if (startIndex != -1)
                {
                    break;
                }
            }
            if (startIndex != -1)
            {
                num = Double.parseDouble(input.substring(startIndex));
            }

            // clear the input and add the new number
            input = "";
            input += num;
            FormatInput();
        }
        else if (clearNextInput)
        {
            input = "";
        }
        
        clearNextInput = false;

        JButton button = (JButton) e.getSource();

        switch (button.getText())
        {
            case "2" + '\u207F' + '\u1D48':
                SwitchToSecondFunctions();
                break;
            case "1" + '\u02E2' + '\u1D57':
                SwitchToFirstFunctions();
                break;
            case "\u03C0":
                input += Math.PI;
                break;
            case "e":
                input += "e";
                break;
            case "C":
                input = "";
                clearNextInput = false;
                break;
            case "\u2190":
                if (input.length() > 0)
                {
                    input = input.substring(0, input.length() - 1);
                }
                break;
            case "x²":
                input += "^2";
                break;
            case "1/x":
                String[] inputArray = input.split(" ");
                double num = Double.parseDouble(inputArray[inputArray.length - 1]);
                input = "";
                for (int i = 0; i < inputArray.length - 1; i++)
                {
                input += inputArray[i];
                }
                input += "1/" + num;
                break;
            case "|x|":
                input += "|";
                break;
            case "exp":
                input += "e^";
                break;
            case "%":
                if (CheckIfSymbol())
                {
                input = input.substring(0, input.length() - 1);
                input += "%";
                }
                else 
                {
                input += "%";
                }
                break;
            case "²√x":
                inputArray = input.split(" ");
                num = Double.parseDouble(inputArray[inputArray.length - 1]);
                input = "";
                for (int i = 0; i < inputArray.length - 1; i++)
                {
                input += inputArray[i];
                }
                input += "2√" + num;
                break;
            case "(":
                input += "(";
                break;
            case ")":
                input += ")";
                break;
            case "n!":
                input += "!";
                break;
            case "\u00F7":
                if (CheckIfSymbol())
                {
                input = input.substring(0, input.length() - 1);
                input += button.getText();
                }
                else 
                {
                input += button.getText();
                }
                break;
            case "xʸ":
                input += "^";
                break;
            case "\u00D7":
                if (CheckIfSymbol())
                {
                input = input.substring(0, input.length() - 1);
                input += button.getText();
                }
                else 
                {
                input += button.getText();
                }
                break;
            case "10ˣ":
                inputArray = input.split(" ");
                num = Double.parseDouble(inputArray[inputArray.length - 1]);
                input = "";
                for (int i = 0; i < inputArray.length - 1; i++)
                {
                input += inputArray[i];
                }
                input += "10^" + num;
                break;
            case "-":
                if(CheckIfSymbol())
                {
                input = input.substring(0, input.length() - 1);
                input += "-";
                }
                else
                {
                input += "-";
                }
                break;
            case "log":
                input += "log(";
                break;
            case "+":
                if (CheckIfSymbol())
                {
                input = input.substring(0, input.length() - 1);
                input += "+";
                }
                else 
                {
                input += "+";
                }
                break;
            case "ln":
                input += "ln(";
                break;
            case "\u00B1":
                // find the last number in the input
                int length = 0;
                num = 0;
                for (int i = input.length() - 1; i >= 0; i--)
                {
                    if (Character.isDigit(input.charAt(i)) || input.charAt(i) == '.')
                    {
                        num = Double.parseDouble(input.substring(i));
                        length = i;
                        break;
                    }
                }
                // remove the last number from the input
                input = input.substring(0, length);
                // add the new number to the input
                if (num < 0)
                {
                    input += Math.abs(num);
                }
                else
                {
                    input += "(-" + num + ")";
                }
                break;
            case ".":
                input += ".";
                break;
            case "=":
                input += "=";
                CalculateInput();
                break;
            case "2ˣ":
                inputArray = input.split(" ");
                num = Double.parseDouble(inputArray[inputArray.length - 1]);
                input = "";
                for (int i = 0; i < inputArray.length - 1; i++)
                {
                input += inputArray[i];
                }
                input += "2^" + num;
                break;
            case "x³":
                input += "^3";
                break;
            case "³√x":
                inputArray = input.split(" ");
                num = Double.parseDouble(inputArray[inputArray.length - 1]);
                input = "";
                for (int i = 0; i < inputArray.length - 1; i++)
                {
                input += inputArray[i];
                }
                input += "3√" + num;
                break;
            case "ʸ√x":
                input += "^(1/";
                break;
            case "logₓ":
                inputArray = input.split(" ");
                num = Double.parseDouble(inputArray[inputArray.length - 1]);
                input = "";
                for (int i = 0; i < inputArray.length - 1; i++)
                {
                input += inputArray[i];
                }
                input += "log" + num + "(";
                break;
            case "eˣ":
                inputArray = input.split(" ");
                num = Double.parseDouble(inputArray[inputArray.length - 1]);
                input = "";
                for (int i = 0; i < inputArray.length - 1; i++)
                {
                input += inputArray[i];
                }
                input += "e^" + num;
                break;
            // default is considered to be only a number
            default:
                input += button.getText();
                break;
        }

        // simplify the input string
        FormatInput();
        
        // display the input string
        textField.setText(input);
    }


    // Calculate the input string
    private void CalculateInput()
    {
        double result = calculations.calculate(input);

        input += Double.toString(result);
        FormatInput();
        clearNextInput = true;
    }

    // Format the input string to remove any ".0" from the end of numbers
    private void FormatInput()
    {
        String[] inputArray = input.split(" ");
        String newInput = "";

        for (int i = 0; i < inputArray.length; i++)
        {
            if (inputArray[i].endsWith(".0"))
            {
                newInput += inputArray[i].substring(0, inputArray[i].length() - 2);
            }
            else
            {
                newInput += inputArray[i];
            }

            if (i != inputArray.length - 1)
            {
                newInput += " ";
            }
        }

        input = newInput;
    }

    // Switch to the second buttons
    private void SwitchToSecondFunctions()
    {
        // clear the buttons panel
        buttonsPanel.removeAll();

        // reset the layout for the buttons
        buttonsPanel.setLayout(new GridLayout(7, 5, 5, 5));

        // create the 2nd buttons
        JButton button;
        for (String label : secondButtonLabels)
        {
            button = new JButton(label);
            // set the font size for the xLogOfYString to be smaller since it's longer
            if (label == logBaseXString)
            {
                button.setFont(new Font("Arial", Font.PLAIN, 13));
            }
            else
            {
                button.setFont(new Font("Arial", Font.PLAIN, 20));
            }
            button.addActionListener(this);
            button.setPreferredSize(new Dimension(70, 65));
            buttonsPanel.add(button);
        }

        // update the window
        revalidate();
        repaint();
    }

    // Switch to the first buttons (or the default buttons)
    private void SwitchToFirstFunctions()
    {
        // clear the buttons panel
        buttonsPanel.removeAll();

        // reset the layout for the buttons
        buttonsPanel.setLayout(new GridLayout(7, 5, 5, 5));

        // create the 1st buttons
        JButton button;
        for (String label : firstButtonLabels)
        {
            button = new JButton(label);
            button.setFont(new Font("Arial", Font.PLAIN, 20));
            button.addActionListener(this);
            button.setPreferredSize(new Dimension(70, 65));
            buttonsPanel.add(button);
        }

        // update the window
        revalidate();
        repaint();
    }

    // Check if the last input is a basic operator (addition, subtraction, multiplication, division, and modulus)
    // NOTE: makes switching between operators when there is a mistake easier
    private boolean CheckIfSymbol()
    {
        // check if the last input is a symbol (or basic operator)
        boolean isSymbol = false;

        // split the input string into an array
        String[] inputArray = input.split(" ");

        // check the last input
        switch (inputArray[inputArray.length - 1])
        {
            case "+":
                isSymbol = true;
                break;
            case "-":
                isSymbol = true;
                break;
            case '\u00D7' + "":
                isSymbol = true;
                break;
            case '\u00F7' + "":
                isSymbol = true;
                break;
            case "%":
                isSymbol = true;
                break;
            default:
                break;
        }

        return isSymbol;
    }
}