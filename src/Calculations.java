/*
 * Calculations.java is the class that performs the calculations for the calculator.
 */

import java.util.Stack;

public class Calculations
{
    // Calculate the result of the expression
    public double calculate(String expression)
    {
        // Create a new instance of the InfixToPostfix class
        InfixToPostfix infixToPostfix = new InfixToPostfix();
        // Convert the infix expression to postfix
        String postfix = infixToPostfix.convert(expression);

        // If the postfix expression is null, throw an exception
        if (postfix == null)
        {
            throw new IllegalArgumentException("Invalid infix expression.");
        }

        // DEBUG CODE
        // System.out.println("Postfix: " + postfix);

        // Evaluate the postfix expression using the evaluatePostfix method
        double result = evaluatePostfix(postfix);

        // DEBUG CODE
        // System.out.println("Result: " + result);

        return result;
    }

    // Evaluate postfix expression
    public double evaluatePostfix(String postfix)
    {
        // Create a new instance of the Stack class
        Stack<Double> stack = new Stack<>();

        // Loop through the postfix expression
        for (int i = 0; i < postfix.length(); i++)
        {
            // Get the character at the current index
            char c = postfix.charAt(i);

            // Skip the '=' character
            if (c == '=')
            {
                continue;
            }

            // If the character is a digit and can be a decimal or a negative number
            if (Character.isDigit(c) || (c == '-') && i + 1 < postfix.length() && Character.isDigit(postfix.charAt(i + 1)))
            {
                // Get the entire number
                String number = "";
                boolean isNegative = false;

                // Check if the number is negative
                if (c == '-')
                {
                    isNegative = true;
                    i++;
                    c = postfix.charAt(i);
                }

                // Get the entire number
                while (i < postfix.length() && (Character.isDigit(c) || c == '.'))
                {
                    number += c;
                    i++;
                    if (i < postfix.length())
                    {
                        c = postfix.charAt(i);
                    }
                }
                i--;

                // Parse the number to a double and push it onto the stack
                double num = Double.parseDouble(number);
                if (isNegative)
                {
                    num = -num;
                }

                // push the number onto the stack
                stack.push(num);
            }
            // If the character is a letter
            else if (Character.isLetter(c))
            {
                // Check for special constants and functions
                if (c == 'e')
                {
                    stack.push(Math.E);
                }
                else if (c == 'π')
                {
                    stack.push(Math.PI);
                }
                else if (c == 'l')
                {
                    // Check for log
                    double operand = stack.pop();
                    double base = stack.pop();
                    double result = log(operand, base);
                    stack.push(result);
                }
                // check for absolute value
                else if (c == 'a' && i + 2 < postfix.length() && postfix.substring(i, i + 3).equals("abs"))
                {
                    i += 2; // Skip the next two characters 'b' and 's'
                    double operand = stack.pop();
                    double result = Math.abs(operand);
                    stack.push(result);
                    continue; // Continue to the next character
                }
                // check for sin
                else if (c == 's' && i + 2 < postfix.length() && postfix.substring(i, i + 3).equals("sin"))
                {
                    i += 2; // Skip the next two characters 'i' and 'n'
                    double operand = stack.pop();
                    double result = sin(operand, false);
                    stack.push(result);
                    continue; // Continue to the next character
                }
                // check for cos
                else if (c == 'c' && i + 2 < postfix.length() && postfix.substring(i, i + 3).equals("cos"))
                {
                    i += 2; // Skip the next two characters 'o' and 's'
                    double operand = stack.pop();
                    double result = cos(operand, false);
                    stack.push(result);
                    continue; // Continue to the next character
                }
                // check for tan
                else if (c == 't' && i + 2 < postfix.length() && postfix.substring(i, i + 3).equals("tan"))
                {
                    i += 2; // Skip the next two characters 'a' and 'n'
                    double operand = stack.pop();
                    double result = tan(operand, false);
                    stack.push(result);
                    continue; // Continue to the next character
                }
                // check for arcsin
                else if (c == 'a' && i + 3 < postfix.length() && postfix.substring(i, i + 4).equals("asin"))
                {
                    i += 4; // Skip the next four characters 'r', 'c', 's', 'i', and 'n'
                    double operand = stack.pop();
                    double result = arcsin(operand, false);
                    stack.push(result);
                    continue; // Continue to the next character
                }
                // check for arccos
                else if (c == 'a' && i + 3 < postfix.length() && postfix.substring(i, i + 4).equals("acos"))
                {
                    i += 4; // Skip the next four characters 'r', 'c', 'o', 's', and 'n'
                    double operand = stack.pop();
                    double result = arccos(operand, false);
                    stack.push(result);
                    continue; // Continue to the next character
                }
                // check for arctan
                else if (c == 'a' && i + 3 < postfix.length() && postfix.substring(i, i + 4).equals("atan"))
                {
                    i += 4; // Skip the next four characters 'r', 'c', 't', 'a', and 'n'
                    double operand = stack.pop();
                    double result = arctan(operand, false);
                    stack.push(result);
                    continue; // Continue to the next character
                }
            }
            // If the character is an operator, pop two operands from the stack, perform the operation, and push the result back onto the stack
            else if (c != ' ')
            {
                // DEBUG CODE
                // System.out.println("Stack before: " + stack);
                // System.out.println("Operator: " + c);

                // Check if there are 1 operands on the stack
                if (stack.size() < 2)
                {
                    // If the operator is '!', pop one operand from the stack and perform the operation
                    if (c == '!')
                    {
                        double operand = stack.pop();
                        double result = performOperation(operand, 0, c);
                        stack.push(result);
                        continue;
                    }
                    // there are not enough operators on the stack to perform an operation
                    else
                    {
                        throw new IllegalArgumentException("Invalid postfix expression: not enough operands.");
                    }
                }

                // Pop two operands from the stack
                double operand2 = stack.pop();
                double operand1 = stack.pop();
                
                // DEBUG CODE
                // System.out.println("Operand1: " + operand1 + " Operand2: " + operand2 + " Operator: " + c);

                // Perform the operation and push the result back onto the stack
                double result = performOperation(operand1, operand2, c);
                stack.push(result);

                // DEBUG CODE
                // System.out.println("Stack: " + stack);
            }
        }

        // The final result will be the only element left on the stack
        return stack.pop();
    }

    // Perform operation
    public double performOperation(double operand1, double operand2, char operator)
    {
        // Perform the operation based on the operator
        double result = 0;
        switch (operator)
        {
            case '+':
                result = add(operand1, operand2);
                break;
            case '-':
                result = subtract(operand1, operand2);
                break;
            // multiplication
            case '\u00D7':
                result = multiply(operand1, operand2);
                break;
            // division
            case '\u00F7':
            case '/':
                result = divide(operand1, operand2);
                break;
            case '%':
                result =  mod(operand1, operand2);
                break;
            case '^':
                result = power(operand1, operand2);
                break;
            // root
            case '\u221A':
                result = root(operand1, operand2);
                break;
            case '!':
                result = factorial(operand1);
                break;
            case '|':
                result = Math.abs(operand1);
                break;
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }

        // round the result to 10 decimal places
        result = Math.round(result * 1e10) / 1e10;

        return result;
    }

    // Basic arithmetic operations
    public double add(double a, double b)
    {
        return a + b;
    }

    public double subtract(double a, double b)
    {
        return a - b;
    }

    public double multiply(double a, double b)
    {
        return a * b;
    }

    public double divide(double a, double b)
    {
        return a / b;
    }

    public double mod(double a, double b)
    {
        return a % b;
    }

    // Exponent and logarithm functions
    public double power(double a, double b)
    {
        return Math.pow(a, b);
    }

    public double root(double a, double b)
    {
        return Math.pow(b, 1 / a);
    }

    public double exp(double a)
    {
        return Math.exp(a);
    }

    public double ln(double a)
    {
        return Math.log(a);
    }

    public double log(double a, double b)
    {
        return Math.log(a) / Math.log(b);
    }

    // Trigonometric functions
    public double sin(double a, boolean degree)
    {
        // if degree is true, convert a to radians
        if (degree)
        {
            a = Math.toRadians(a);
        }
        
        // Calculate the sin of a
        double result = Math.sin(a);

        // Define a small tolerance value
        double tolerance = 1e-10;
        // If the result is less than the tolerance value, return 0
        if (Math.abs(result) < tolerance)
        {
            return 0;
        }
        
        return result;
    }

    public double cos(double a, boolean degree)
    {
        // if degree is true, convert a to radians then return the degree value of cos
        if (degree)
        {
            a = Math.toRadians(a);
            
        }

        // Calculate the cos of a
        double result = Math.cos(a);

        // Define a small tolerance value
        double tolerance = 1e-10;
        // If the result is less than the tolerance value, return 0
        if (Math.abs(result) < tolerance)
        {
            return 0;
        }

        return result;
    }

    public double tan(double a, boolean degree)
    {
        // if degree is true, convert a to radians then return the degree value of tan
        if (degree)
        {
            a = Math.toRadians(a);
        }
        
        // Calculate the tan of a
        double result = Math.tan(a);

        // Define a small tolerance value
        double tolerance = 1e-10;
        // If the result is less than the tolerance value, return 0
        if (Math.abs(result) < tolerance)
        {
            return 0;
        }

        return result;
    }

    public double csc(double a, boolean degree)
    {
        // if degree is true, convert a to radians then return the degree value of csc
        if (degree)
        {
            a = Math.toRadians(a);
        }
        
        double csc = 1 / Math.sin(a);

        // Define a small tolerance value
        double tolerance = 1e-10;
        // If the result is less than the tolerance value, return 0
        if (Math.abs(csc) < tolerance)
        {
            return 0;
        }

        return csc;
    }

    public double sec(double a, boolean degree)
    {
        // if degree is true, convert a to radians then return the degree value of sec
        if (degree)
        {
            a = Math.toRadians(a);
        }
        
        double sec = 1 / Math.cos(a);

        // Define a small tolerance value
        double tolerance = 1e-10;
        // If the result is less than the tolerance value, return 0
        if (Math.abs(sec) < tolerance)
        {
            return 0;
        }

        return sec;
    }

    public double cot(double a, boolean degree)
    {
        // if degree is true, convert a to radians then return the degree value of cot
        if (degree)
        {
            a = Math.toRadians(a);
        }
        
        double cot = 1 / Math.tan(a);

        // Define a small tolerance value
        double tolerance = 1e-10;
        // If the result is less than the tolerance value, return 0
        if (Math.abs(cot) < tolerance)
        {
            return 0;
        }

        return cot;
    }

    // Inverse trigonometric functions
    public double arcsin(double a, boolean degree)
    {
        // if degree is true, convert a to radians then return the degree value of arcsin
        if (degree)
        {
            a = Math.toRadians(a);
        }
        
        double arcsin = Math.asin(a);

        // Define a small tolerance value
        double tolerance = 1e-10;
        // If the result is less than the tolerance value, return 0
        if (Math.abs(arcsin) < tolerance)
        {
            return 0;
        }

        return arcsin;
    }

    public double arccos(double a, boolean degree)
    {
        // if degree is true, convert a to radians then return the degree value of arccos
        if (degree)
        {
            a = Math.toRadians(a);
        }
        
        double arccos = Math.acos(a);

        // Define a small tolerance value
        double tolerance = 1e-10;
        // If the result is less than the tolerance value, return 0
        if (Math.abs(arccos) < tolerance)
        {
            return 0;
        }

        return arccos;
    }

    public double arctan(double a, boolean degree)
    {
        // if degree is true, convert a to radians then return the degree value of arctan
        if (degree)
        {
            a = Math.toRadians(a);
        }
        
        double arctan = Math.atan(a);

        // Define a small tolerance value
        double tolerance = 1e-10;
        // If the result is less than the tolerance value, return 0
        if (Math.abs(arctan) < tolerance)
        {
            return 0;
        }

        return arctan;
    }

    public double arccsc(double a, boolean degree)
    {
        // if degree is true, convert a to radians then return the degree value of arccsc
        if (degree)
        {
            a = Math.toRadians(a);
        }
        
        double arccsc = Math.asin(1 / a);

        // Define a small tolerance value
        double tolerance = 1e-10;
        // If the result is less than the tolerance value, return 0
        if (Math.abs(arccsc) < tolerance)
        {
            return 0;
        }

        return arccsc;
    }

    public double arcsec(double a, boolean degree)
    {
        // if degree is true, convert a to radians then return the degree value of arcsec
        if (degree)
        {
            a = Math.toRadians(a);
        }
        
        double arcsec = Math.acos(1 / a);

        // Define a small tolerance value
        double tolerance = 1e-10;
        // If the result is less than the tolerance value, return 0
        if (Math.abs(arcsec) < tolerance)
        {
            return 0;
        }

        return arcsec;
    }

    public double arccot(double a, boolean degree)
    {
        // if degree is true, convert a to radians then return the degree value of arccot
        if (degree)
        {
            a = Math.toRadians(a);
        }
        
        double arccot = Math.atan(1 / a);

        // Define a small tolerance value
        double tolerance = 1e-10;
        // If the result is less than the tolerance value, return 0
        if (Math.abs(arccot) < tolerance)
        {
            return 0;
        }

        return arccot;
    }

    // Factorial
    public double factorial(double a)
    {
        if (a == 0)
        {
            return 1;
        }
        else
        {
            return a * factorial(a - 1);
        }
    }
}
