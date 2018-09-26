package chatbotLogic;

/**
 *
 * @author cicciog
 */
public class TransposList {
    
    private static String transposList[][] = {
			{"I'M", "YOU'RE"},
			{"AM", "ARE"},
			{"WERE", "WAS"},
			{"ME", "YOU"},
			{"YOURS", "MINE"},
			{"YOUR", "MY"},
			{"I'VE", "YOU'VE"},
			{"I", "YOU"},
			{"AREN'T", "AM NOT"},
			{"WEREN'T", "WASN'T"},
			{"I'D", "YOU'D"},
			{"DAD", "FATHER"},
			{"MOM", "MOTHER"},
			{"DREAMS", "DREAM"},
			{"MYSELF", "YOURSELF"}
		};

    public String[][] getTransposList() {
        return transposList;
    }
    
    
}
