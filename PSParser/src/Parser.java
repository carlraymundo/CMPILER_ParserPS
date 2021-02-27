import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;


public class Parser {

    int address;

    public String parse(ArrayList<String> input, HashMap<String, Rule> rules, String start, ArrayList<String> sInput){
        Stack<String> stack = new Stack<String>();
        Stack<String> inputStack = new Stack<String>();
        Stack<String> recordStack = new Stack<String>();

        boolean noRule = false;

        inputStack.push("$");
        int error_address = 0;

        for(int i=(input.size()-1);i>=0;i--)
            inputStack.push(input.get(i));

        stack.push(start);

        while(!(stack.isEmpty())&&!(noRule))
            if(stack.peek().equals("epsilon")){
                stack.pop();
                address = 0;
            }
            else{
                if(Character.isLowerCase(stack.peek().charAt(0))){
                    expandRule(stack, recordStack, rules.get(stack.peek()).RHS);
                }

                else if(Character.isUpperCase(stack.peek().charAt(0))){
                    if(inputStack.peek().equals(stack.peek())){
                        inputStack.pop();
                        stack.pop();
                        address = 0;
                        error_address++;

                        while(!recordStack.isEmpty())
                            recordStack.pop();
                    }
                    else
                    if(recordStack.isEmpty())
                        noRule = true;
                    else
                        backtrack(stack, recordStack);
                }

            }

        if(inputStack.peek()=="$"&&stack.isEmpty())
            return "- ACCEPT";

        else if(inputStack.peek()=="$"&&!stack.isEmpty()){
            error_address--;
            return "- REJECT. Missing after \'" + sInput.get(error_address) +"\'";
        }
        else
            return "- REJECT. Offending Token \'" + sInput.get(error_address)+"\'";
    }


    public void expandRule(Stack<String> stack1, Stack<String> stack2, ArrayList<String> lines){

        stack1.pop();

        String[] temp = lines.get(0).split(" ");
        for(int i=(lines.size()-1);i>0;i--)
            stack2.push(lines.get(i));

        for(int j=temp.length-1;j>=0;j--){
            stack1.push(temp[j]);
            if(!stack2.isEmpty())
                address++;
        }
    }

    public void backtrack(Stack<String> stack1, Stack<String> stack2){
        for(int i=0;i<address;i++)
            stack1.pop();
        address = 0;
        if (!stack2.isEmpty()){
            String[] track = stack2.pop().split(" ");

            for(int j=track.length-1;j>=0;j--){
                stack1.push(track[j]);
                address++;
            }
        }
    }
}
