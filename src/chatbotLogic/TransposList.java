/*
MIT License

Copyright (c) 2017 cicciog

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
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
