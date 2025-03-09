import java.util.Stack;

public class Calculations
{
    // Calculate
    public double calculate(String expression)
    {
        // Create a new instance of the InfixToPostfix class
        InfixToPostfix infixToPostfix = new InfixToPostfix();
        // Convert the infix expression to postfix
        String postfix = infixToPostfix.convert(expression);

        if (postfix == null)
        {
            throw new IllegalArgumentException("Invalid infix expression.");
        }

        System.out.println("Postfix: " + postfix);

        // Evaluate the postfix expression using the evaluatePostfix method
        double result = evaluatePostfix(postfix);

        System.out.println("Result: " + result);

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
                if (c == '-')
                {
                    isNegative = true;
                    i++;
                    c = postfix.charAt(i);
                }
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
                stack.push(num);
            }
            else if (Character.isLetter(c))
            {
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
                    double operand = stack.pop();
                    double base = stack.pop();
                    double result = log(operand, base);
                    stack.push(result);
                }
                else if (c == 'a' && i + 2 < postfix.length() && postfix.substring(i, i + 3).equals("abs"))
                {
                    i += 2; // Skip the next two characters 'b' and 's'
                    double operand = stack.pop();
                    double result = Math.abs(operand);
                    stack.push(result);
                    continue; // Continue to the next character
                }
            }
            // If the character is an operator, pop two operands from the stack, perform the operation, and push the result back onto the stack
            else if (c != ' ')
            {
                System.out.println("Stack before: " + stack);

                System.out.println("Operator: " + c);
                if (stack.size() < 2)
                {
                    if (c == '!')
                    {
                        double operand = stack.pop();
                        double result = performOperation(operand, 0, c);
                        stack.push(result);
                        continue;
                    }
                    else
                    {
                        throw new IllegalArgumentException("Invalid postfix expression: not enough operands.");
                    }
                }
                double operand2 = stack.pop();
                double operand1 = stack.pop();
                System.out.println("Operand1: " + operand1 + " Operand2: " + operand2 + " Operator: " + c);
                double result = performOperation(operand1, operand2, c);
                stack.push(result);

                System.out.println("Stack: " + stack);
            }
        }

        // The final result will be the only element left on the stack
        return stack.pop();
    }

    // Perform operation
    public double performOperation(double operand1, double operand2, char operator)
    {
        double result = 0;
        switch (operator)
        {
            case '+':
                result = add(operand1, operand2);
                break;
            case '-':
                result = subtract(operand1, operand2);
                break;
            case '\u00D7':
                result = multiply(operand1, operand2);
                break;
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
