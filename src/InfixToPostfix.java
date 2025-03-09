import java.util.Stack;

public class InfixToPostfix
{
    public String convert(String infix)
    {
        String postfix = "";
        Stack<Character> stack = new Stack<>();
        boolean secondPipe = false; // Flag to track the second '|'
        
        for (int i = 0; i < infix.length(); i++)
        {
            char c = infix.charAt(i);
            if (Character.isDigit(c) || c == '.')
            {
                postfix += c;
                if (i + 1 < infix.length() && !Character.isDigit(infix.charAt(i + 1)) && infix.charAt(i + 1) != '.')
                {
                    postfix += ' ';
                }
            }
            else if (c == '|')
            {
                if (secondPipe)
                {
                    while (!stack.isEmpty() && stack.peek() != '|')
                        postfix += stack.pop();
                    if (!stack.isEmpty() && stack.peek() == '|')
                        stack.pop();
                    postfix += "abs ";
                    secondPipe = false;
                }
                else
                {
                    stack.push(c);
                    secondPipe = true;
                }
            }
            else if (c == '(')
                stack.push(c);
            else if (c == ')')
            {
                while (!stack.isEmpty() && stack.peek() != '(')
                    postfix += stack.pop();
                if (!stack.isEmpty() && stack.peek() != '(')
                    return null;
                else
                    stack.pop();
            }
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
                else
                {
                    postfix += "10 ";
                    i += 2;
                }

                // add the log to the stack of operators
                stack.push(c);
            }
            else if (c == 'l' && i + 1 < infix.length() && infix.substring(i, i + 2).equals("ln"))
            {
                postfix += "e ";
                i += 1;
                stack.push(c);
            }
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
            else
            {
                while (!stack.isEmpty() && precedence(c) <= precedence(stack.peek()))
                {
                    if (c == '|' && stack.peek() == '|')
                    {
                        if (secondPipe)
                        {
                            postfix += stack.pop();
                            secondPipe = false;
                        }
                        else
                        {
                            stack.pop();
                            secondPipe = true;
                        }
                    }
                    else
                    {
                        postfix += stack.pop();
                    }
                }
                stack.push(c);
            }
        }

        while (!stack.isEmpty())
            postfix += stack.pop();
        return postfix;
    }

    int precedence(char c)
    {
        switch(c)
        {
            case '+':
            case '-':
                return 1;
            case '\u00D7':
            case '\u00F7':
            case '/':
            case '%':
            case '!':
                return 2;
            case '^':
            case '\u221A':
                return 3;
        }
        return -1;
    }
}
