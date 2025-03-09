import java.util.Stack;

public class PostfixEvaluation
{
    public int evaluate(String postfix)
    {
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < postfix.length(); i++)
        {
            char c = postfix.charAt(i);
            if (Character.isDigit(c))
                stack.push(c - '0');
            else
            {
                int val1 = stack.pop();
                int val2 = stack.pop();
                switch(c)
                {
                    case '+':
                        stack.push(val2 + val1);
                        break;
                    case '-':
                        stack.push(val2 - val1);
                        break;
                    case '\u00D7':
                        stack.push(val2 * val1);
                        break;
                    case '\u00F7':
                        stack.push(val2 / val1);
                        break;
                }
            }
        }
        return stack.pop();
    }
}
