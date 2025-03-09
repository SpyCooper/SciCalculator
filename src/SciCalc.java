import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class SciCalc extends JFrame implements ActionListener
{
    // Create an instance of the Calculations class
    Calculations calculations = new Calculations();

    // Create a string to store the input
    String input = "";
    JTextField textField;

    // Create a boolean to check if the next input should be cleared
    boolean clearNextInput = false;

    // Create a boolean to check if the second button is clicked
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

    // Create a dropdown for the trigonometric functions
    JComboBox<String> trigDropdown;

    // Runs the calculator
    public void run()
    {
        CalculatorVisual();
    }

    // Create the starting visual for the calculator
    private void CalculatorVisual()
    {
        // Create a window
        setTitle("Scientific Calculator");
        setSize(450, 600);

        // Set the layout for the window
        setLayout(new BorderLayout(10, 10)); // add padding between components

        // Make the window resizable
        setResizable(true);

        // Exit the program when the user closes the window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a field to display input and output on the top of the window
        textField = new JTextField(12);
        textField.setFont(new Font("Arial", Font.PLAIN, 40));
        textField.setHorizontalAlignment(JTextField.RIGHT);
        textField.setEditable(false); // make the text field read-only
        add(textField, BorderLayout.NORTH);

        // create the trigonometric dropdown
        CreateTrigDropdown();

        // Create the default buttons
        SwitchToFirstFunctions();

        // Add a component listener to handle resizing
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // get the width and height of the window
                int width = getWidth();
                int height = getHeight();
                // set the font size for the text field
                textField.setFont(new Font("Arial", Font.PLAIN, width / 15));
                // set the buttons to be a percentage of the window size
                for (Component comp : buttonsPanel.getComponents()) {
                    if (comp instanceof JButton) {
                        JButton button = (JButton) comp;
                        button.setFont(new Font("Arial", Font.PLAIN, width / 25));
                        button.setPreferredSize(new Dimension(width / 7, height / 10));
                    }
                }
                // update the window
                buttonsPanel.revalidate();
            }
        });

        // Add padding around the edges of the window
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
                // check if the input is a number
                if (Character.isDigit(input.charAt(i)) || input.charAt(i) == '.')
                {
                    startIndex = i;
                }
                // check if the input is a negative number
                else if (input.charAt(i) == '-' && (i == 0 || !Character.isDigit(input.charAt(i - 1))))
                {
                    startIndex = i;
                    break;
                }
                // check if the input is a symbol
                else if (startIndex != -1)
                {
                    break;
                }
            }
            // get the number from the input
            if (startIndex != -1)
            {
                num = Double.parseDouble(input.substring(startIndex));
            }

            // clear the input and add the new number
            input = "";
            input += num;

            // simplify the input string
            FormatInput();
        }
        // check if the input needs to be cleared
        else if (clearNextInput)
        {
            input = "";
        }
        
        // reset the clearNextInput boolean
        clearNextInput = false;

        // get the button that was clicked
        JButton button = (JButton) e.getSource();

        // check which button was clicked
        switch (button.getText())
        {
            // 2nd button
            case "2" + '\u207F' + '\u1D48':
                SwitchToSecondFunctions();
                break;
            // 1st button
            case "1" + '\u02E2' + '\u1D57':
                SwitchToFirstFunctions();
                break;
            // pi
            case "\u03C0":
                input += Math.PI;
                break;
            // e
            case "e":
                input += "e";
                break;
            // clear the input
            case "C":
                input = "";
                clearNextInput = false;
                break;
            // backspace
            case "\u2190":
                if (input.length() > 0)
                {
                    input = input.substring(0, input.length() - 1);
                }
                break;
            // x²
            case "x²":
                input += "^2";
                break;
            // 1/x
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
            // |x|
            case "|x|":
                input += "|";
                break;
            // exponent
            case "exp":
                input += "e^";
                break;
            // modulus
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
            // square root
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
            // open parenthesis
            case "(":
                input += "(";
                break;
            // close parenthesis
            case ")":
                input += ")";
                break;
            // factorial
            case "n!":
                input += "!";
                break;
            // division
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
            // x^y
            case "xʸ":
                input += "^";
                break;
            // multiplication
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
            // 10^x
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
            // subtraction
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
            // log base 10
            case "log":
                input += "log(";
                break;
            // addition
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
            // natural log
            case "ln":
                input += "ln(";
                break;
            // sign change
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
            // decimal point
            case ".":
                input += ".";
                break;
            // equals
            case "=":
                input += "=";
                CalculateInput();
                break;
            // 2^x
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
            // x³
            case "x³":
                input += "^3";
                break;
            // ³√x
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
            // y√x
            case "ʸ√x":
                input += "^(1/";
                break;
            // log base x
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
            // e^x
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
            // sin
            case "sin":
                input += "sin(";
                break;
            // cos
            case "cos":
                input += "cos(";
                break;
            // tan
            case "tan":
                input += "tan(";
                break;
            // asin
            case "asin":
                input += "asin(";
                break;
            // acos
            case "acos":
                input += "acos(";
                break;
            // atan
            case "atan":
                input += "atan(";
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
        // check if there are any errors in the input
        try
        {
            double result = calculations.calculate(input);
            input += Double.toString(result);
        }
        catch (Exception e)
        {
            input += "ERROR";
        }

        // display the input string
        FormatInput();

        // clear the next input
        clearNextInput = true;
    }

    // Format the input string to remove any ".0" from the end of numbers
    private void FormatInput()
    {
        // split the input string into an array
        String[] inputArray = input.split(" ");
        // create a new input string
        String newInput = "";

        // remove any ".0" from the end of numbers
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

        // set the new input string
        input = newInput;
    }

    private void CreateTrigDropdown()
    {
        // add a dropdown with the trigonometric functions
        String[] trigFunctions = {"sin", "cos", "tan", "asin", "acos", "atan"};
        trigDropdown = new JComboBox<>(trigFunctions);
        trigDropdown.setFont(new Font("Arial", Font.PLAIN, 20));
        // add an action listener to the dropdown to add the selected function to the input
        trigDropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // check if the input needs to be cleared
                if (clearNextInput)
                {
                    input = "";
                }
                clearNextInput = false;
                
                // get the selected item from the dropdown
                String selectedFunction = (String) trigDropdown.getSelectedItem();
                // add the selected function to the input
                input += selectedFunction + "(";
                // display the input string
                textField.setText(input);
            }
        });
        // add the dropdown to the window
        add(trigDropdown, BorderLayout.CENTER);
    }

    // Switch to the second buttons
    private void SwitchToSecondFunctions()
    {
        SwitchFunctions(secondButtonLabels);
    }

    // Switch to the first buttons (or the default buttons)
    private void SwitchToFirstFunctions()
    {
        SwitchFunctions(firstButtonLabels);
    }

    // Switch between first and second functions
    private void SwitchFunctions(String[] buttonLabels)
    {
        // clear the buttons panel
        buttonsPanel.removeAll();

        // reset the layout for the buttons
        buttonsPanel.setLayout(new GridLayout(7, 5, 5, 5));

        // create the buttons
        JButton button;
        for (String label : buttonLabels)
        {
            button = new JButton(label);
            // set the font size for the xLogOfYString to be smaller since it's longer
            if (label.equals(logBaseXString))
            {
                button.setFont(new Font("Arial", Font.PLAIN, 13));
            }
            else
            {
                button.setFont(new Font("Arial", Font.PLAIN, 20));
            }
            // add an action listener to the button
            button.addActionListener(this);

            // set the button size based on the window size
            button.setPreferredSize(new Dimension(getWidth() / 7, getHeight() / 10));

            // add the button to the panel
            buttonsPanel.add(button);
        }
        
        // Add the panel to the window
        add(buttonsPanel, BorderLayout.SOUTH);

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