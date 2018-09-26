package chatbotLogic;

import GUI.MyFrame;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Vector;
import jdk.nashorn.internal.parser.TokenType;

/**
 *
 * @author cicciog
 */
public class ManagerChat {

    private static String input = new String("");
    private static String response = new String("");
    private static String previousInput = new String("");
    private static String previousResponse = new String("");
    private static String event = new String("");
    private static String previousEvent = new String("");
    private static String inputBackup = new String("");
    private static String subject = new String("");
    private static String keyWord = new String("");
    private static boolean quitProgram = false;

    private static Knowledgebase kb = new Knowledgebase();
    private static String[][][] knowledgebase = kb.getKnowledgeBase();

    private static TransposList tl = new TransposList();
    private static String transposList[][] = tl.getTransposList();

    final static int maxInput = 1;
    final static int maxResponse = 6;
    final static String delim = "?!.;,";

    private static Vector<String> responseList = new Vector<String>(maxResponse);

    //Prende l'input dall'utente
    public static void getInput(String pInput) throws Exception {
        
        savePreviousInput();
        input = pInput;
        preprocessInput();
    }

    //Risposta del chatbot
    public static void respond() {
        savePreviousResponse();
        setEvent("BOT UNDERSTAND**");

        if (nullInput()) {
            handleEvent("NULL INPUT**");
        } else if (nullInputRepetition()) {
            handleEvent("NULL INPUT REPETITION**");
        } else if (userRepeat()) {
            handleUserRepetition();
        } else {
            findMatching();
        }

        if (userWantToQuit()) {
            quitProgram = true;
        }

        if (!botUnderstand()) {
            handleEvent("BOT DON'T UNDERSTAND**");
        }

        if (responseList.size() > 0) {
            selectResponse();

            if (botRepeat()) {
                handleRepetition();
            }
        }
    }

    public static boolean quit() {
        return quitProgram;
    }

    //Effettua una ricerca dell'input dello user nella base di conoscenza
    public static void findMatching() {
        responseList.clear();
        // Supporta il processo di classificazione delle parole chiavi durante il processo di confronto
        String bestKeyWord = "";
        Vector<Integer> indexVector = new Vector<Integer>(maxResponse);

        for (int i = 0; i < knowledgebase.length; ++i) {
            String[] keyWordList = knowledgebase[i][0];

            for (int j = 0; j < keyWordList.length; ++j) {
                String keyWord = keyWordList[j];
                //inserisce uno spazio prima e dopo la parola chiave per migliorare la fase di confronto
                keyWord = insertSpace(keyWord);

                if (input.indexOf(keyWord) != -1) {
                    //keyword ranking
                    if (keyWord.length() > bestKeyWord.length()) {
                        bestKeyWord = keyWord;
                        indexVector.clear();
                        indexVector.add(i);
                    } else if (keyWord.length() == bestKeyWord.length()) {
                        indexVector.add(i);
                    }
                }
            }
        }
        if (indexVector.size() > 0) {
            keyWord = bestKeyWord;
            Collections.shuffle(indexVector);
            int respIndex = indexVector.elementAt(0);
            int respSize = knowledgebase[respIndex][1].length;
            for (int j = 0; j < respSize; ++j) {
                responseList.add(knowledgebase[respIndex][1][j]);
            }
        }
    }

    //Pre elaborazione della risposta
    void preprocessResponse() {
        if (response.indexOf("*") != -1) {
            //estrazione dall'input
            findSubject();
            //utilizzo della lista di trasposizione
            subject = transposeString(subject);
            response = response.replaceFirst("*", subject);
        }
    }

    void findSubject() {
        subject = "";
        StringBuffer buffer = new StringBuffer(input);
        buffer.deleteCharAt(0);
        input = buffer.toString();
        int pos = input.indexOf(keyWord);
        if (pos != -1) {
            subject = input.substring(pos + keyWord.length() - 1, input.length());
        }
    }

    //Implementazione della funzione di trasposizione
    public static String transposeString(String str) {
        boolean bTransposed = false;
        for (int i = 0; i < transposList.length; ++i) {
            String first = transposList[i][0];
            insertSpace(first);
            String second = transposList[i][1];
            insertSpace(second);

            String backup = str;
            str = str.replaceFirst(first, second);
            if (str != backup) {
                bTransposed = true;
            }
        }

        if (!bTransposed) {
            for (int i = 0; i < transposList.length; ++i) {
                String first = transposList[i][0];
                insertSpace(first);
                String second = transposList[i][1];
                insertSpace(second);
                str = str.replaceFirst(first, second);
            }
        }
        return str;
    }

    //Gestisce le ripetizioni effettuate dal programma
    public static void handleRepetition() {
        if (responseList.size() > 0) {
            responseList.removeElementAt(0);
        }
        if (noResponse()) {
            saveInput();
            setInput(event);

            findMatching();
            restoreInput();
        }
        selectResponse();
    }

    //Gestisce le ripetizioni effettuate dall'utente
    public static void handleUserRepetition() {
        if (sameInput()) {
            handleEvent("REPETITION T1**");
        } else if (similarInput()) {
            handleEvent("REPETITION T2**");
        }
    }

    //Gestisce gli eventi
    public static void handleEvent(String str) {
        savePreviousEvent();
        setEvent(str);

        saveInput();
        str = insertSpace(str);

        setInput(str);

        if (!sameEvent()) {
            findMatching();
        }

        restoreInput();
    }

    public static String starting() {
        handleEvent("SIGNON**");
        selectResponse();
        return printResponse();
    }

    //Seleziona le risposte da un elenco di risposte
    public static void selectResponse() {
        Collections.shuffle(responseList);
        response = responseList.elementAt(0);
    }

    //Memorizza l'input corrente prima di ricevere un nuovo input
    public static void savePreviousInput() {
        previousInput = input;
    }

    //Memorizza la risposta data in passato, prima di cercare la risposta corrente
    public static void savePreviousResponse() {
        previousResponse = response;
    }

    //Memorizza l'evento che si è verificato, ad esempio inserimento nullo
    public static void savePreviousEvent() {
        previousEvent = event;
    }

    //Imposta l'evento attuale
    public static void setEvent(String str) {
        event = str;
    }

    public static void saveInput() {
        inputBackup = input;
    }

    public static void setInput(String str) {
        input = str;
    }

    //Memorizza l'input corrente
    public static void restoreInput() {
        input = inputBackup;
    }

    public static String printResponse() {
        if (response.length() > 0) {
            return response;
        }
        return " ";
    }

    //Effettua una pre-elaborazione prima per il controllo dell'input
    public static void preprocessInput() {
        input = cleanString(input);
        input = input.toUpperCase();
        input = insertSpace(input);
    }

    //Verifica se il chatterbot ha cominciato a ripetersi
    public static boolean botRepeat() {
        return (previousResponse.length() > 0 && response == previousResponse);
    }

    //Verifica se lo user ha ripetuto un input precedentemente immesso
    public static boolean userRepeat() {
        return (previousInput.length() > 0 && ((input == previousInput)|| (input.indexOf(previousInput) != -1)|| (previousInput.indexOf(input) != -1)));
    }

    //Verifica se il bot comprende l'input inserito
    public static boolean botUnderstand() {
        return responseList.size() > 0;
    }

    //Verifica se l'input attualmente inserito è nullo
    public static boolean nullInput() {
        return (input.length() == 0 && previousInput.length() != 0);
    }

    //Verifica se l'utente ha ripetuto alcuni input nulli

    public static boolean nullInputRepetition() {
        return (input.length() == 0 && previousInput.length() == 0);
    }

    //Verifica se l'utente vuole abbandonare la conversazione

    public static boolean userWantToQuit() {
        return input.indexOf("BYE") != -1;
    }

    //Verifica se l'evento attuale è uguale al precedente

    public static boolean sameEvent() {
        return (event.length() > 0 && event == previousEvent);
    }

    //Controlla se il programma non ha la risposta per l'input corrente
    public static boolean noResponse() {
        return responseList.size() == 0;
    }

    //Verifica se l'ingresso corrente è uguale al precedente
    public static boolean sameInput() {
        return (input.length() > 0 && input == previousInput);
    }

    //Verifica se l'input corrente e simile ai precedenti
    public static boolean similarInput() {
        return (input.length() > 0 && (input.indexOf(previousInput) != -1 || previousInput.indexOf(input) != -1));
    }

    static boolean isPunc(char ch) {
        return delim.indexOf(ch) != -1;
    }

    //Rimuove la punteggiatura e gli spazi in eccesso nell'input
    static String cleanString(String str) {
        StringBuffer temp = new StringBuffer(str.length());
        char prevChar = 0;
        for (int i = 0; i < str.length(); ++i) {
            if ((str.charAt(i) == ' ' && prevChar == ' ') || !isPunc(str.charAt(i))) {
                temp.append(str.charAt(i));
                prevChar = str.charAt(i);
            } else if (prevChar != ' ' && isPunc(str.charAt(i))) {
                temp.append(' ');
            }

        }
        return temp.toString();
    }

    //Inserisce gli spazi prima e dopo una parola chiave
    static String insertSpace(String str) {
        StringBuffer temp = new StringBuffer(str);
        temp.insert(0, ' ');
        temp.insert(temp.length(), ' ');
        return temp.toString();
    }

}
