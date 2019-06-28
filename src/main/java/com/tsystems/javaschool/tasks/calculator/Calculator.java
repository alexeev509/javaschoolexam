package com.tsystems.javaschool.tasks.calculator;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Calculator {

    /**
     * Evaluate statement represented as string.
     *
     * @param statement mathematical statement containing digits, '.' (dot) as decimal mark,
     *                  parentheses, operations signs '+', '-', '*', '/'<br>
     *                  Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
     * @return string value containing result of evaluation or null if statement is invalid
     */
    private static String rpnString;
    private static Stack<Character> characterStack=new Stack<Character>();
    private static Stack<Double> numbersStack=new Stack<Double>();
    private static Map<Character,Integer> priorityMap=new HashMap<Character, Integer>();
    private static String REGEX = "[0-9]+";

    static {
        priorityMap.put('*',3);
        priorityMap.put('/',3);
        priorityMap.put('+',2);
        priorityMap.put('-',2);
        priorityMap.put('(',1);
        priorityMap.put(')',-1);
    }

    public static String convertMathematicalExpressionToRPN(String mathematicalExpression){

        rpnString="";
        mathematicalExpression=mathematicalExpression.replaceAll(" ","");
        int priority=0;//if this is number; Number has priority 0;
        for (int i = 0; i < mathematicalExpression.length(); i++) {
            if(priorityMap.containsKey(mathematicalExpression.charAt(i)))
                priority=priorityMap.get(mathematicalExpression.charAt(i));
            else
                priority=0;

            if(priority==0)
                rpnString+=mathematicalExpression.charAt(i);
            if(priority==1)
                characterStack.push(mathematicalExpression.charAt(i));
            if(priority>1){
                rpnString+=' ';//?????
                while (!characterStack.isEmpty()){
                    if(priorityMap.get(characterStack.peek())>=priority)
                        rpnString+=characterStack.pop();
                    else
                        break;
                }
                characterStack.push(mathematicalExpression.charAt(i));
            }

            if(priority==-1){
                rpnString+=' ';
                while (priorityMap.get(characterStack.peek())!=1)
                    rpnString+=characterStack.pop();
                //remove '(' from stack
                characterStack.pop();
            }

        }
        //Добавлям оставшиеся в стэке данные к результирующей строке
        while (!characterStack.isEmpty())
            rpnString+=characterStack.pop();

        return rpnString;
    }


    public static Double convertRpnToAnswer(String rpnString){
        String operand="";

        for(int i=0;i<rpnString.length();i++){
            if(rpnString.charAt(i)!=' '){
                if(!priorityMap.containsKey(rpnString.charAt(i))){
                    while (rpnString.charAt(i)!=' ' && !priorityMap.containsKey(rpnString.charAt(i))){
                        operand+=rpnString.charAt(i++);
                        if(i==rpnString.length())
                            break;
                    }
                    numbersStack.push(Double.parseDouble(operand));
                    operand="";
                }
                if(rpnString.charAt(i)!=' ' && priorityMap.get(rpnString.charAt(i))>1) {
                    double a = numbersStack.pop();
                    double b = numbersStack.pop();
                    if(rpnString.charAt(i)=='+')
                        numbersStack.push(b+a);
                    if(rpnString.charAt(i)=='-')
                        numbersStack.push(b-a);
                    if(rpnString.charAt(i)=='*')
                        numbersStack.push(b*a);
                    if(rpnString.charAt(i)=='/')
                        numbersStack.push(b/a);

                }
            }
        }

        return numbersStack.pop();
    }


    public boolean validationOfMathematicalExpression(String mathematicalExpression){

        //
        //if count of( != count of )


        //
        if(mathematicalExpression==null || mathematicalExpression=="")
            return false;

        int countOfOpenBrackets=0;
        int countOfClosedBrackets=0;
        for(int i=0;i<mathematicalExpression.length();i++){
            if(mathematicalExpression.charAt(i)=='(')
                countOfOpenBrackets++;
            else if(mathematicalExpression.charAt(i)==')')
                countOfClosedBrackets++;
            if(mathematicalExpression.charAt(i)!='.' &&
                    !priorityMap.containsKey(mathematicalExpression.charAt(i)) &&
                    !String.valueOf(mathematicalExpression.charAt(i)).matches(REGEX))
                return false;
        }
        if(countOfClosedBrackets!=countOfOpenBrackets)
            return false;





        mathematicalExpression=mathematicalExpression.replaceAll(" ","");



        //check first and last symbols - they mus be numbers or (  )!
        if((mathematicalExpression.charAt(0)!='(' &&
                !String.valueOf(mathematicalExpression.charAt(0)).matches(REGEX))||
                (mathematicalExpression.charAt(mathematicalExpression.length()-1)!=')'&&
                        !String.valueOf(mathematicalExpression.charAt(mathematicalExpression.length()-1)).matches(REGEX))
                )
            return false;

        for(int i=1;i<mathematicalExpression.length()-1;i++){
            //after '.' not a number -it is impossible
            if(mathematicalExpression.charAt(i)=='.' && !String.valueOf(mathematicalExpression.charAt(i+1)).matches(REGEX))
                return false;
                //if current char is +/-/ * / / before must be number or ')' after must be bumber or '('
            else  if(priorityMap.containsKey(mathematicalExpression.charAt(i)) &&
                    priorityMap.get(mathematicalExpression.charAt(i))>1 &&
                    ((!String.valueOf(mathematicalExpression.charAt(i-1)).matches(REGEX) &&  mathematicalExpression.charAt(i-1)!=')') ||
                            (!String.valueOf(mathematicalExpression.charAt(i+1)).matches(REGEX) &&  mathematicalExpression.charAt(i+1)!='(')))
                return false;
                //if current char ='(' after can be number/ or (
            else  if (priorityMap.containsKey(mathematicalExpression.charAt(i))&&
                    priorityMap.get(mathematicalExpression.charAt(i))==1 &&
                    ((!String.valueOf(mathematicalExpression.charAt(i+1)).matches(REGEX) &&
                            mathematicalExpression.charAt(i+1)!='(')))
                return false;
        }

        return true;
    }

    public String evaluate(String mathematicalExpression) {
        if(!validationOfMathematicalExpression(mathematicalExpression))
            return null;
        String actualResult=String.valueOf(convertRpnToAnswer(convertMathematicalExpressionToRPN(mathematicalExpression)));
        System.out.println(actualResult.substring(actualResult.indexOf('.')+1, actualResult.length()));
        if("0".equals(actualResult.substring(actualResult.indexOf('.')+1,actualResult.length()))){
            return actualResult.substring(0,actualResult.indexOf('.'));
        }
        if("Infinity".equals(actualResult))
            return null;
        return  actualResult;
    }

}
