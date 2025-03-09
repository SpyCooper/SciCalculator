/*
 * InfixToPostfix.java is a class that converts an infix expression to a postfix expression.
 * This is used to evaluate the expression in the SciCalc class following the order of operations.
 */

 import java.util.Stack;

public class InfixToPostfix
{
    // Convert an infix expression to a postfix expression
    public String convert(String infix)
    {
        // create a postfix string to store the converted infix expression
        String postfix = "";
        Stack<Character> stack = new Stack<>();

        // check if the infix expression has a second pipes
        boolean secondPipe = false;

        // iterate through the infix expression
        for (int i = 0; i < infix.length(); i++)
        {
            // get the character at the current index
            char c = infix.charAt(i);

            // check if the character is a digit or a decimal point
            if (Character.isDigit(c) || c == '.')
            {
                // add the digit or decimal point to the postfix to make a number
                postfix += c;

                // check if the next character is not a digit or a decimal point
                if (i + 1 < infix.length() && !Character.isDigit(infix.charAt(i + 1)) && infix.charAt(i + 1) != '.')
                {
                    // add a space to separate the numbers
                    postfix += ' ';
                }
            }
            // if the character is part of an absolute value
            else if (c == '|')
            {
                // check if the second pipe has been reached
                if (secondPipe)
                {
                    // add the absolute value to the postfix
                    while (!stack.isEmpty() && stack.peek() != '|')
                        postfix += stack.pop();
                    if (!stack.isEmpty() && stack.peek() == '|')
                        stack.pop();
                    postfix += "abs ";

                    // reset the second pipe
                    secondPipe = false;
                }
                // if the first pipe has been reached
                else
                {
                    // add the pipe to the stack of operators
                    stack.push(c);

                    // set the second pipe to true
                    secondPipe = true;
                }
            }
            // if the character is an open parenthesis
            else if (c == '(')
                stack.push(c);
            // if the character is a close parenthesis
            else if (c == ')')
            {
                // add the operators inside the parentheses to the postfix
                while (!stack.isEmpty() && stack.peek() != '(')
                    postfix += stack.pop();
                if (!stack.isEmpty() && stack.peek() != '(')
                    return null;
                else
                    stack.pop();
            }
            // if the c is L and the next two characters are og
            else if (c == 'l' && i + 2 < infix.length() && infix.substring(i, i + 3).equals("log"))
            {
                // check if the log has a base defined
                if (Character.isDigit(infix.charAt(i + 3)))
                {
                    // get the entire base
                    String base = "";
                    int j = i + 3;
                    while (j < infix.length() && (Character.isDigit(infix.charAt(j)) || infix.charAt(j) == '.'))
                    {
                        base += infix.charAt(j);
                        j++;
                    }
                    // add the base to the postfix
                    postfix += base + " ";
                    // skip the base in the infix
                    i = j - 1;
                }
                // if the log does not have a base
                else
                {
                    postfix += "10 ";
                    i += 2;
                }

                // add the log to the stack of operators
                stack.push(c);
            }
            // if the c is L and the next character is n
            else if (c == 'l' && i + 1 < infix.length() && infix.substring(i, i + 2).equals("ln"))
            {
                // set the postfix to have a base of e
                postfix += "e ";
                i += 1;
                stack.push(c);
            }
            // if the next word is sin
            else if (c == 's' && i + 2 < infix.length() && infix.substring(i, i + 3).equals("sin"))
            {
                // find the value inside the parentheses
                String value = "";
                int j = i + 4;
                while (j < infix.length() && infix.charAt(j) != ')')
                {
                    value += infix.charAt(j);
                    j++;
                }
                // add the value to the postfix to be evaluated
                postfix += convert(value) + " ";
                // skip the value in the infix
                i = j;
                // add the sin to the postfix
                postfix += "sin ";
            }
            // if the next word is cos
            else if (c == 'c' && i + 2 < infix.length() && infix.substring(i, i + 3).equals("cos"))
            {
                // find the value inside the parentheses
                String value = "";
                int j = i + 4;
                while (j < infix.length() && infix.charAt(j) != ')')
                {
                    value += infix.charAt(j);
                    j++;
                }
                // add the value to the postfix to be evaluated
                postfix += convert(value) + " ";
                // skip the value in the infix
                i = j;
                // add the cos to the postfix
                postfix += "cos ";
            }
            // if the next word is tan
            else if (c == 't' && i + 2 < infix.length() && infix.substring(i, i + 3).equals("tan"))
            {
                // find the value inside the parentheses
                String value = "";
                int j = i + 4;
                while (j < infix.length() && infix.charAt(j) != ')')
                {
                    value += infix.charAt(j);
                    j++;
                }
                // add the value to the postfix to be evaluated
                postfix += convert(value) + " ";
                // skip the value in the infix
                i = j;
                // add the tan to the postfix
                postfix += "tan ";
            }
            // if the next word is asin
            else if (c == 'a' && i + 3 < infix.length() && infix.substring(i, i + 4).equals("asin"))
            {
                // find the value inside the parentheses
                String value = "";
                int j = i + 5;
                while (j < infix.length() && infix.charAt(j) != ')')
                {
                    value += infix.charAt(j);
                    j++;
                }
                // add the value to the postfix to be evaluated
                postfix += convert(value) + " ";
                // skip the value in the infix
                i = j;
                // add the asin to the postfix
                postfix += "asin ";
            }
            // if the next work is acos
            else if (c == 'a' && i + 3 < infix.length() && infix.substring(i, i + 4).equals("acos"))
            {
                // find the value inside the parentheses
                String value = "";
                int j = i + 5;
                while (j < infix.length() && infix.charAt(j) != ')')
                {
                    value += infix.charAt(j);
                    j++;
                }
                // add the value to the postfix to be evaluated
                postfix += convert(value) + " ";
                // skip the value in the infix
                i = j;
                // add the acos to the postfix
                postfix += "acos ";
            }
            // if the next word is atan
            else if (c == 'a' && i + 3 < infix.length() && infix.substring(i, i + 4).equals("atan"))
            {
                // find the value inside the parentheses
                String value = "";
                int j = i + 5;
                while (j < infix.length() && infix.charAt(j) != ')')
                {
                    value += infix.charAt(j);
                    j++;
                }
                // add the value to the postfix to be evaluated
                postfix += convert(value) + " ";
                // skip the value in the infix
                i = j;
                // add the atan to the postfix
                postfix += "atan ";
            }
            // if the character is - and the previous character is an operator or an open parenthesis
            else if (c == '-' && (i == 0 || infix.charAt(i - 1) == '(' || !Character.isDigit(infix.charAt(i - 1)) && infix.charAt(i - 1) != ')'))
            {
                // Handle negative numbers
                postfix += c;
                i++;
                while (i < infix.length() && (Character.isDigit(infix.charAt(i)) || infix.charAt(i) == '.'))
                {
                    postfix += infix.charAt(i);
                    i++;
                }
                postfix += ' ';
                i--;
            }
            // if the character is an operator
            else
            {
                // add the operators to the postfix
                while (!stack.isEmpty() && precedence(c) <= precedence(stack.peek()))
                    postfix += stack.pop();
                // add the operator to the stack
                stack.push(c);
            }
        }

        // add the remaining operators to the postfix
        while (!stack.isEmpty())
            postfix += stack.pop();
        return postfix;
    }

    // Get the precedence of an operator
    int precedence(char c)
    {
        switch(c)
        {
            // all variations of addition and subtraction
            case '+':
            case '-':
                return 1;
            // all variations of multiplication and division
            case '\u00D7':
            case '\u00F7':
            case '/':
            case '%':
            case '!':
                return 2;
            // all variations of exponentiation and square root
            case '^':
            case '\u221A':
                return 3;
        }

        // if the operator is not recognized
        return -1;
    }
}
