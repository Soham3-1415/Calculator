import java.util.Scanner;
import java.util.Stack;

public class Calculator {

	public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter expression:: ");
        String expression = input.nextLine();
        expression=expression.replace('[', '(');
        expression=expression.replace(']', ')');
        String[] splitExpression = expression.split(" ");
        expression = "";
        for (String term:splitExpression)
        		expression+=term;
        expression=toPostfix(expression);
        System.out.print(solvePostfix(expression));
	}
	
	private static String toPostfix(String expression) {
		String operators = "-+*/()";
		String[] tokens = expression.split("(?<=["+operators+"])|(?=["+operators+"])");
		Stack<String> operatorStack = new Stack<String>();
		String output;
		System.out.println(operatorStack.peek());
		return "";
		for (String token:tokens) {
			if (isDouble(token))
				output+=token;
			else if(token.equals("("))
				operatorStack.push(token);
			else if(token.equals(")")) {
				if(operatorStack.search("(") <= 0)
					throw new IllegalArgumentException("Mismatched parenthesis.");
				while(operatorStack.search("(")>1)
					output+=operatorStack.pop();
				operatorStack.pop();
			}
			else {
				while(((!operatorStack.empty())&&(()||())&&()))
					output+=operatorStack.pop();
				operatorStack.push(token);
			}
		}
		while(operatorStack.size() > 0) {
			if(operatorStack.search("(") > 0)
				throw new IllegalArgumentException("Mismatched parenthesis.");
			output+=operatorStack.pop();
		}
		return output;
	}

	public static double solvePostfix(String expression) {
		Stack<Double> postStack = new Stack<Double>();
		String[] terms = expression.split(" ");
		for(String term:terms) {
			if(isDouble(term))
				postStack.push(Double.parseDouble(term));
			else {
				double a = postStack.pop();
				double b = postStack.pop();
				switch (term.charAt(0)) {
					case '+': postStack.push(a+b); break;				
					case '-': postStack.push(a-b); break;
					case '*': postStack.push(a*b); break;
					case '/': if(b==0)
							  	throw new IllegalArgumentException("Cannot divide by 0.");
								postStack.push(a/b); break;
					case '^': postStack.push(Math.pow(a, b)); break;
				}
			}
		}
		return postStack.pop();
	}
	
	public static boolean isDouble (String input) {
		try {
			Double.parseDouble(input);
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}
}
