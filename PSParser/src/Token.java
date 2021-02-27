public class Token {

    enum Types{
        INT,
        ADD,
        MULT,
        LeftP,
        RightP,
        LeftB,
        RightB,
        ERROR
    }

    public static Types token_type;
    public static String lexeme;

    private static final int q0 = 0; // start
    private static final int q1 = 1; // int
    private static final int q2 = 2; // plus sign
    private static final int q3 = 3; // mult sign
    private static final int q4 = 4; // Left Paren
    private static final int q5 = 5; // Right Paren
    private static final int q6 = 6; // Left Bracket
    private static final int q7 = 7; // Right Bracket
    private static final int q8 = -1; //Error

    public static int state;

    //Adopted from DFA.java from ProbSet1
    public int dfa(int state, char c) {
        switch(state){
            case q0:switch(c){
                case'1':

                case'2':

                case'3':

                case'4':

                case'5':

                case'6':

                case'7':

                case'8':

                case'9':

                case'0':
                    return q1;
                case'*':
                    return q2;
                case'+':
                    return q3;
                case'(':
                    return q4;
                case')':
                    return q5;
                case'[':
                    return q6;
                case']':
                    return q7;
                default:
                    return q8;
            }
            case q1:switch(c){
                case'0':

                case'1':

                case'2':

                case'3':

                case'4':

                case'5':

                case'6':

                case'7':

                case'8':

                case'9':
                    return q1;
                default:
                    return q8;
            }
            default:
                return q8;
        }
    }

    public boolean accepted() {
        if(state != q8 && state != q0)
            return true;
        else
            return false;
    }

    public void process(String a) {
        int i;
        for (i=0;i<a.length();i++){
            state = dfa(state, a.charAt(i));
        }

    }

    public Token(String word){
        token_type= check(word);
        lexeme = word;
    }

    public Types check(String word){
        state = 0;

        process(word);

        if (accepted()){
            if (state==q1)
                return Types.INT;
            else if(state==q2)
                return Types.ADD;
            else if(state==q3)
                return Types.MULT;
            else if(state==q4)
                return Types.LeftP;
            else if(state==q5)
                return Types.RightP;
            else if(state==q6)
                return Types.LeftB;
            else if(state==q7)
                return Types.RightB;
            else
                return Types.ERROR;
        }
        else
            return Types.ERROR;
    }

}
