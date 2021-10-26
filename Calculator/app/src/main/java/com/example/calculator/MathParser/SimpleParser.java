package com.example.calculator.MathParser;
import java.lang.String;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleParser implements MathParser
{
    private final ArrayList<Character> allowedOperators = new ArrayList<>();
    private final Character pointSign;

    public SimpleParser(){
        allowedOperators.add('+');
        allowedOperators.add('-');
        allowedOperators.add('*');
        allowedOperators.add('/');
        pointSign = ',';
    }

    @Override
    public  boolean checkExpression(String expression) {
        //con not start with operator
        boolean operatorFlag = false;
        //english notation ,1 = 0,1
        boolean pointFlag = true;

        for (char character: expression.toCharArray()) {
            //operator entered
            if (allowedOperators.contains(character)){
                //2 ops in a row
                if(!operatorFlag)
                    return false;
                operatorFlag = false;
                pointFlag = true;
            }
            //comma entered
            else if(character == pointSign){
                //2 commas in a number
                if(!pointFlag)
                    return false;
                pointFlag = false;
            }
            //digit entered
            else if(Character.isDigit(character))
            {
                operatorFlag = true;
            }
            //char not allowed
            else
            {
                return false;
            }
        }
        return true;
    }

    @Override
    public String calculateExpression(String expression) throws RuntimeException, ArithmeticException {
        if (!checkExpression(expression))
            throw new RuntimeException("Illegal Syntax");

        String mathExpression = expression.replaceAll(" ", "");
        mathExpression = expression.replaceAll(pointSign.toString(), "");


        Pattern dividePattern = Pattern.compile("([0-9]*\\.?[0-9]+)(/)([0-9]*\\.?[0-9]+)");
        Pattern multiplyPattern = Pattern.compile("([0-9]*\\.?[0-9]+)(\\*)([0-9]*\\.?[0-9]+)");
        Pattern subtractPattern = Pattern.compile("([0-9]*\\.?[0-9]+)(-)([0-9]*\\.?[0-9]+)");
        Pattern addPattern = Pattern.compile("([0-9]*\\.?[0-9]+)(\\+)([0-9]*\\.?[0-9]+)");

        String result = "";

        Matcher matcher = dividePattern.matcher(mathExpression);
        while(matcher.find())
        {
            //group 0 = everything, 1 = first bracket,...
             result = calculate(matcher.group(1), matcher.group(3), matcher.group(2));
             mathExpression = matcher.replaceAll(result);
        }

        matcher = multiplyPattern.matcher(mathExpression);
        while(matcher.find())
        {
            result = calculate(matcher.group(1), matcher.group(3), matcher.group(2));
            mathExpression = matcher.replaceAll(result);
        }

        matcher = subtractPattern.matcher(mathExpression);
        while(matcher.find())
        {
            result = calculate(matcher.group(1), matcher.group(3), matcher.group(2));
            mathExpression = matcher.replaceAll(result);
        }

        matcher = addPattern.matcher(mathExpression);
        while(matcher.find())
        {
            result = calculate(matcher.group(1), matcher.group(3), matcher.group(2));
            mathExpression = matcher.replaceAll(result);
        }

        return mathExpression.replaceAll("\\.", pointSign.toString());
    }

    private String calculate(String firstOperand, String secondOperand, String operation) throws RuntimeException, ArithmeticException
    {
        Double fOp = Double.parseDouble(firstOperand);
        Double sOp = Double.parseDouble(secondOperand);

        switch (operation)
        {
            case "+":
                return Double.toString(fOp + sOp);
            case "-":
                return Double.toString(fOp - sOp);
            case "*":
                return Double.toString(fOp * sOp);
            case "/":
                if(sOp.equals(0.0))
                    throw new ArithmeticException("Division by Zero");
                return Double.toString(fOp / sOp);
            default:
                throw new RuntimeException("Tokenizer Error");
        }

    }
}
