import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Calculator {

	public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter expression:: ");
        String expression = input.nextLine();
        input.close();
        expression=expression.replace('[', '(');
        expression=expression.replace(']', ')');
        String[] splitExpression = expression.split(" ");
        expression = "";
        for (String term:splitExpression)
        		expression+=term;
        System.out.println(solvePostfix(toPostfix(expression)));
	}
	
	private static Queue<String> toPostfix(String expression) {
		String operators = "-+*/^()";
		String[] tokens = expression.split("(?<=["+operators+"])|(?=["+operators+"])");
		Stack<String> operatorStack = new Stack<String>();
		Queue<String> output = new LinkedList<String>();
		for (String token:tokens) {
			if (isDouble(token))
				output.offer(token);
			else if(token.equals("("))
				operatorStack.push(token);
			else if(token.equals(")")) {
				if(operatorStack.search("(") <= 0)
					throw new IllegalArgumentException("Mismatched parenthesis.");
				while(operatorStack.search("(")>1)
					output.offer(operatorStack.pop());
				operatorStack.pop();
			}
			else {
				while(((!operatorStack.isEmpty())&&(!computeFirst(token, operatorStack.peek()))&&(!operatorStack.peek().equals("("))))
					output.offer(operatorStack.pop());
				operatorStack.push(token);
			}
		}
			while(!operatorStack.isEmpty()) {
				if(operatorStack.search("(") > 0)
					throw new IllegalArgumentException("Mismatched parenthesis.");
				output.offer(operatorStack.pop());
		}
		return output;
	}
	
	public static boolean computeFirst(String operator1, String operator2) {
		if(getPemdasOrder(operator1) > getPemdasOrder(operator2))
			return true;
		else if (getPemdasOrder(operator1) < getPemdasOrder(operator2))
			return false;
		else
			return isLeftAssociative(operator1);
	}
	
	public static boolean isLeftAssociative(String operator1) {
		switch (operator1.charAt(0)) {
			case '^': return false;
			case '+': return true;
			case '-': return true;
			case '/': return true;
			case '*': return true;
			default: return true;
		}
	}
	
	public static int getPemdasOrder(String operator) {
		switch(operator.charAt(0)) {
			case '^': return 3;
			case '*': return 2;
			case '/': return 2;
			case '+': return 1;
			case '-': return 1;
			default: return 0;
			
		}
	}
	
	public static double solvePostfix(Queue<String> expression) {
		Stack<Double> postStack = new Stack<Double>();
		while(!expression.isEmpty()) {
			String term = expression.poll();
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
