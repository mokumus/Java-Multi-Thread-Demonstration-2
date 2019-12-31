package com.muhammedokumus;

import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.*;

import static java.lang.System.currentTimeMillis;

/**
 * Main class for multi thread testing visualization
 */
public class TextAreaOutputStream extends OutputStream {
    private final JTextArea textTerminal;
    private final StringBuilder sb = new StringBuilder();

    public TextAreaOutputStream(final JTextArea textTerminal) {
        this.textTerminal = textTerminal;
    }

    @Override
    public void flush() {}

    @Override
    public void close() {}

    @Override
    public void write(int b) throws IOException {
        if (b == '\r') {
            return;
        }

        if (b == '\n') {
            final String text = sb.toString() + "\n";
            textTerminal.append(text);
            sb.setLength(0);
        }
        else {
            sb.append((char) b);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Setup threads*******************************
            var ref = new Object() {
                int numOfThreads = 0;
            };
            // *******************************

            // Create GUI elements*******************************
            JFrame frame = new JFrame(TextAreaOutputStream.class.getSimpleName());
            JTextArea textTerminal = new JTextArea(24, 80);
            JButton buttonStart = new JButton("Start");
            buttonStart.setEnabled(false);
            String sizes[] = {"16", "64", "128", "256"};
            JComboBox sizeSelect =new JComboBox(sizes);
            sizeSelect.setSelectedIndex(0);
            JRadioButton rb1 = new JRadioButton("1 Thread");
            JRadioButton rb2 = new JRadioButton("2 Threads");
            JRadioButton rb3 = new JRadioButton("4 Threads");
            ButtonGroup bg = new ButtonGroup();
            bg.add(rb1);
            bg.add(rb2);
            bg.add(rb3);
            JScrollPane scrollPane = new JScrollPane (textTerminal, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            // *******************************

            //Populate Frame *******************************
            frame.add(scrollPane, BorderLayout.CENTER);
            frame.add(rb1);
            frame.add(rb2);
            frame.add(rb3);
            frame.add(sizeSelect);
            frame.add(buttonStart);
            // *******************************

            // Setup Frame *******************************
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            System.setOut(new PrintStream(new TextAreaOutputStream(textTerminal)));
            frame.getContentPane().setLayout(new FlowLayout());
            frame.pack();
            frame.setVisible(true);
            // *******************************

            // Redirect system.out to text area
            System.setOut(new PrintStream(new TextAreaOutputStream(textTerminal)));

            rb1.addActionListener(e -> {
                ref.numOfThreads =1;
                buttonStart.setEnabled(true);
            });

            rb2.addActionListener(e -> {
                ref.numOfThreads =2;
                buttonStart.setEnabled(true);
            });

            rb3.addActionListener(e -> {
                ref.numOfThreads =4;
                buttonStart.setEnabled(true);
            });

            // BUTTON: EXIT
            buttonStart.addActionListener(e -> {
                System.out.println("Hello");
                int size = Integer.parseInt((String) sizeSelect.getSelectedItem());
                System.out.println("Selected size: " + size);
                float[][] eq1= new float[size][size];
                float[][] eq2= new float[size][size];

                for(float[] row : eq1){
                    Arrays.fill(row, 1);
                }
                for(float[] row : eq2){
                    Arrays.fill(row, 1);
                }

                MatrixTS m1 = new MatrixTS(eq1);
                MatrixTS m2 = new MatrixTS(eq2);



                List<Thread> threads = new ArrayList<>();
                for(int i = 0; i < ref.numOfThreads; i ++){
                    System.out.println("Thread created: " + i);
                    int workAmount = m1.getRowSize()/ ref.numOfThreads;
                    Thread t = new Thread(new Worker(m1,m2,i*workAmount,workAmount));
                    threads.add(t);
                }

                long start = currentTimeMillis();
                for(Thread t : threads)
                    t.start();

                for(Thread t : threads) {
                    try {
                        t.join();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }

                //ready for dft
                long end = currentTimeMillis();
                threads.clear();
                m1.display();
                System.out.println("#Size: " + size);
                System.out.println("#Threads: " + ref.numOfThreads);
                System.out.println("Time elapsed: " + (end-start)/1000.0);
                System.out.println("Ready for DFT...");
                textTerminal.setCaretPosition(textTerminal.getDocument().getLength());
            });

            Thread currentThread = Thread.currentThread();
            System.out.println("Main thread: " + currentThread.getName() + "(" + currentThread.getId() + ")");
        });
    }
}
