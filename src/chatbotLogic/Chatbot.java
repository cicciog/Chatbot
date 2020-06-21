package chatbotLogic;

import GUI.MyFrame;

/**
 *
 * @author cicciog
 */
public class Chatbot {

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        ManagerChat mc = new ManagerChat();
        MyFrame myframe = new MyFrame(mc);

        try {
            myframe.appendResponse(mc.starting() + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
