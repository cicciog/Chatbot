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
package GUI;

import chatbotLogic.ManagerChat;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author cicciog
 */
public class MyFrame extends JFrame {

    public ManagerChat mc;

    private ImageIcon image = new ImageIcon("./images/bot.gif");

    private Dimension screenDim = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
    private JButton sendBtn = new JButton();
    private JScrollPane scrollPane = new JScrollPane();
    private JTextArea chatArea = new JTextArea();
    private JTextField messageField = new JTextField();
    private JLabel imageLabel;

    public MyFrame(ManagerChat pMc) {
        setTitle("Chatbot");
        setBackground(Color.white);
        setLayout(null);
        setSize(screenDim.width, screenDim.height);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponents(pMc);
        setVisible(true);
    }

    private void initComponents(ManagerChat pMc) {

        mc = pMc;

        imageLabel = new JLabel(image);
        imageLabel.setSize(screenDim.width / 36 * 15, screenDim.height / 18 * 12);
        imageLabel.setLocation(screenDim.width / 36 * 17, screenDim.height / 18 * 2);
        
        add(imageLabel);
        
        chatArea.setSize(screenDim.width / 36 * 15, screenDim.height / 18 * 12);
        chatArea.setLocation(screenDim.width / 36 * 2, screenDim.height / 18 * 2);

        javax.swing.border.Border border = BorderFactory.createLineBorder(Color.cyan);

        chatArea.setBorder(border);
        scrollPane.setViewportView(chatArea);

        add(chatArea);

        messageField.setSize(screenDim.width / 36 * 8, screenDim.height / 18);
        messageField.setLocation(screenDim.width / 36 * 2, screenDim.height / 18 * 15);
        messageField.setText("Insert message here...");
        add(messageField);

        sendBtn.setSize(screenDim.width / 36 * 2, screenDim.height / 18);
        sendBtn.setLocation(screenDim.width / 36 * 10, screenDim.height / 18 * 15);
        sendBtn.setText("Send");
        add(sendBtn);

        sendBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                try {
                    sendBtnMouseClicked(evt);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            private void sendBtnMouseClicked(MouseEvent evt) throws Exception {

                chatArea.append("- " + messageField.getText() + "\n");
                mc.getInput(messageField.getText());
                mc.respond();
                chatArea.append(mc.printResponse() + "\n");

            }
        });
    }

    public void appendResponse(String pResponse) {
        String response = pResponse;
        chatArea.append(response);
    }

}

