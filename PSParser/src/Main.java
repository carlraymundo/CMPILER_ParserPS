import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

class Main {

    public static void main(String[] args) {
        BufferedReader reader, gReader;
        ArrayList<Rule> ruleArrayList = new ArrayList<>();
        HashMap<String, Rule> rules = new HashMap<String,Rule>();
        String output = new String();

        //Reading File from Grammar and add to ruleArrayList
        try{

            gReader = new BufferedReader(new FileReader("Textfiles/grammar.txt"));
            String grammar_line = gReader.readLine();

            while (grammar_line!=null){
                if(grammar_line==" ")
                    grammar_line = gReader.readLine();
                Rule temp = new Rule();
                String[] words = grammar_line.split(":");
                String[] r = words[1].split("\\|");

                temp.LHS = words[0].trim();

                for(int i=0; i<r.length; i++){
                    temp.RHS.add(r[i].trim());
                }


                ruleArrayList.add(temp);
                rules.put(temp.LHS, temp);

                grammar_line = gReader.readLine();
            }
            gReader.close();
        } catch (IOException e){
            e.printStackTrace();
        }

        //Reading File from testcase
        try {
            reader = new BufferedReader(new FileReader("Textfiles/testcase.txt"));
            String token_line = reader.readLine();
            ArrayList<Token> token_list = new ArrayList<Token>();
            while (token_line != null)
                if (token_line.trim().length()==0)
                    token_line = reader.readLine();
                else {

                    ArrayList<String> tokens = new ArrayList<String>();
                    ArrayList<String> inputs = new ArrayList<String>();
                    Parser parser = new Parser();

                    String temp = new String("");

                    for (int j=0; j<token_line.length(); j++){
                        if (token_line.charAt(j) < 48 || token_line.charAt(j) > 57)
                            temp = temp.concat(" " + token_line.charAt(j) + " ");
                        else
                            temp = temp.concat("" + token_line.charAt(j));
                    }


                    String[] s = temp.split(" ");

                    for (int i=0;i<s.length;i++){
                        if (s[i].length()>0) {
                            Token t = new Token(s[i]);

                            token_list.add(t);
                            String tTemp = t.token_type.toString();
                            inputs.add(s[i]);
                            tokens.add(tTemp);
                        }
                    }

                    output = output.concat(token_line + " " + parser.parse(tokens, rules, ruleArrayList.get(0).LHS, inputs) + "\n");
                    token_line = reader.readLine();
                }

            reader.close();

            File file = new File("Textfiles/output.txt"); // Writing file
            FileWriter filewriter = new FileWriter(file);

            try {
                filewriter.write(output);
                filewriter.close();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (filewriter!=null)
                        filewriter.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}