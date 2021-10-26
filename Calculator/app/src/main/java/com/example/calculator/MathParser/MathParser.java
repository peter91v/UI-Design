package com.example.calculator.MathParser;
import java.lang.String;

public interface MathParser
{
    /**Takes a Math String and checks it for Syntax errors*/
    public  boolean checkExpression(String expression);

    /**Calculate the given Meth expression
     * @throws RuntimeException when given String includes non valid math syntax
     * @throws ArithmeticException when dividing by zero
     * x*/
    public String calculateExpression(String expression) throws RuntimeException, ArithmeticException;
}
