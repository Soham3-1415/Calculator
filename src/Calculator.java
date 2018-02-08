import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Calculator {

	public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter expression:: ");
        StringBuilder expression = new StringBuilder(input.nextLine());
        input.close();
        expression = new StringBuilder(expression.toString().replace('[', '('));
        expression = new StringBuilder(expression.toString().replace(']', ')'));
        String[] splitExpression = expression.toString().split(" ");
        expression = new StringBuilder();
        for (String term:splitExpression)
        		expression.append(term);
        System.out.println(solvePostfix(toPostfix(expression.toString())));
	}
	
	private static Queue<String> toPostfix(String expression) {
		String operators = "-+*/()";
		String[] tokens = expression.split("(?<=["+operators+"])|(?=["+operators+"])");
		Stack<String> operatorStack = new Stack<>();
		Queue<String> output = new LinkedList<>();
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
	
	private static boolean computeFirst(String operator1, String operator2) {
		return getPemdasOrder(operator1) > getPemdasOrder(operator2) || getPemdasOrder(operator1) >= getPemdasOrder(operator2) && isLeftAssociative(operator1);
	}
	
	private static boolean isLeftAssociative(String operator1) {
		switch (operator1.charAt(0)) {
			case '+': return true;
			case '-': return true;
			case '/': return true;
			case '*': return true;
			default: return true;
		}
	}
	
	private static int getPemdasOrder(String operator) {
		switch(operator.charAt(0)) {
			case '*': return 2;
			case '/': return 2;
			case '+': return 1;
			case '-': return 1;
			default: return 0;
			
		}
	}
	
	private static double solvePostfix(Queue<String> expression) {
		Stack<Double> postStack = new Stack<>();
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
					default: throw new IllegalArgumentException("Operator not supported.");
				}
			}
		}
		return postStack.pop();
	}
	
	private static boolean isDouble(String input) {
		try {
			Double.parseDouble(input);
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}
}
