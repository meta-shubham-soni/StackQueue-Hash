/**
 * this class evaluate a infix expression using Stack
 */
public class EvaluateInfix {
	
	/**
	 * Function to evaluate the expression
	 * @param expression
	 * @return
	 */
    public static int evaluate(String expression){
    	
        char[] tokens = expression.toCharArray();
        MyStack values = new MyStack();
        MyStack ops = new MyStack();
 
        for (int i = 0; i < tokens.length; i++){
            if (tokens[i] == ' ')
                continue;
 
            if (tokens[i] >= '0' && tokens[i] <= '9'){
                StringBuffer sbuf = new StringBuffer();
        
                while (i < tokens.length && tokens[i] >= '0' && tokens[i] <= '9')
                    sbuf.append(tokens[i++]);
                
                values.push(Integer.parseInt(sbuf.toString()));
                         
                i--;
            }
 
            else if (tokens[i] == '(')
                ops.push(tokens[i]);
 
            else if (tokens[i] == ')'){
                while (ops.opPeek() != '(')
                  values.push(applyOp(ops.opPop(),values.intPop(),values.intPop()));
                ops.opPop();
            }
 
          
            else if (tokens[i] == '+' || tokens[i] == '-' || tokens[i] == '*' || tokens[i] == '/'){
                while (!ops.isEmpty() && hasPrecedence(tokens[i],ops.opPeek()))
                  values.push(applyOp(ops.opPop(),values.intPop(),values.intPop()));
 
                ops.push(tokens[i]);
            }
        }
 
  
        while (!ops.isEmpty())
            values.push(applyOp(ops.opPop(),values.intPop(),values.intPop()));
 
     
        return values.intPop();
    }

    /**
     * Function to check which operator has higher precedence
     * @param op1
     * @param op2
     * @return
     */
    public static boolean hasPrecedence(char op1, char op2){
        if (op2 == '(' || op2 == ')')
            return false;
        if ((op1 == '*' || op1 == '/') &&
            (op2 == '+' || op2 == '-'))
            return false;
        else
            return true;
    }
 
    /**
     * Function to apply op operation between a & b
     * @param op
     * @param b
     * @param a
     * @return
     */
    public static int applyOp(char op,int b, int a){
        switch (op)
        {
        case '+':
            return a + b;
        case '-':
            return a - b;
        case '*':
            return a * b;
        case '/':
            if (b == 0)
                throw new
                UnsupportedOperationException(
                      "Cannot divide by zero");
            return a / b;
        }
        return 0;
    }
	 
}