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
            case '|':
                return 3;
        }
        return -1;
    }
}
