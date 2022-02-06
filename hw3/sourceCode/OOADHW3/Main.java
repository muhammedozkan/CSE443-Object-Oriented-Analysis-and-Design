package OOADHW3;

import OOADHW3.Q2.AddressBook;
import OOADHW3.Q2.AddressBookGroup;
import OOADHW3.Q2.AddressBookPerson;
import OOADHW3.Q3.Complex;
import OOADHW3.Q3.DFTMatrixClassicSync;
import OOADHW3.Q3.DFTMatrixMutexSync;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Main {
    static int N = 512;

    static Complex[][] a = new Complex[N][N];
    static Complex[][] b = new Complex[N][N];

    static Thread testThread1;
    static Thread testThread1sub;

    static Thread testThread4;
    static Thread testThread4sub1;
    static Thread testThread4sub2;
    static Thread testThread4sub3;
    static Thread testThread4sub4;

    static JTextArea messageTxt;


    public static void main(String[] args) {
        //Creating the Frame
        JFrame frame = new JFrame("OOADHW3-Q3");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 600);


        JPanel panel = new JPanel();

        JButton startST = new JButton("Start Single Thread");
        JButton cancelST = new JButton("Cancel Single Thread");
        startST.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                testThread1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Complex.fillRandomComplex(a, 0, 0, N, N);
                        messageTxt.append("\nSingle Thread# A matris fill random number.\n");

                        Complex.fillRandomComplex(b, 0, 0, N, N);
                        messageTxt.append("Single Thread# B matris fill random number.\n");

                        List<String> sync = new ArrayList<String>();

                        int threadSize = 1;

                        Instant inst1 = Instant.now();

                        Runnable r0 = new DFTMatrixClassicSync(a, b, 0, 0, N, N, N, sync, 1);
                        testThread1sub = new Thread(r0);
                        testThread1sub.start();

                        messageTxt.append("Single Thread# A+B calculation and DFT calculation.\n");

                        synchronized (sync) {
                            while (sync.size() < threadSize * 2) {
                                try {

                                    sync.wait(); // (Releases lock, and reacquires on wakeup)
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        Instant inst2 = Instant.now();
                        messageTxt.append("\nSingle Thread# Elapsed Time: " + Duration.between(inst1, inst2).toString() + "\n");
                        startST.setEnabled(true);
                        cancelST.setEnabled(false);
                    }
                });
                testThread1.start();
                startST.setEnabled(false);
                cancelST.setEnabled(true);
            }
        });


        cancelST.setEnabled(false);
        cancelST.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                testThread1sub.stop();
                testThread1.stop();
                startST.setEnabled(true);
                cancelST.setEnabled(false);
                messageTxt.append("Single Thread# Cancel Operation\n");
            }
        });

        JButton start4T = new JButton("Start 4 Thread");
        JButton cancel4T = new JButton("Cancel 4 Thread");

        start4T.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                testThread4 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Complex.fillRandomComplex(a, 0, 0, N, N);

                        messageTxt.append("\n4 Thread(Mutex)# A matris fill random number.\n");

                        Complex.fillRandomComplex(b, 0, 0, N, N);
                        messageTxt.append("4 Thread(Mutex)# B matris fill random number.\n");

                        Semaphore mutex = new Semaphore(0);

                        int threadSize = 4;

                        Instant inst1 = Instant.now();

                        Runnable r0 = new DFTMatrixMutexSync(a, b, 0, 0, N / 2, N / 2, N, mutex, 4);
                        testThread4sub1 = new Thread(r0);

                        Runnable r1 = new DFTMatrixMutexSync(a, b, 0, N / 2, N / 2, N, N, mutex, 4);
                        testThread4sub2 = new Thread(r1);

                        Runnable r2 = new DFTMatrixMutexSync(a, b, N / 2, 0, N, N / 2, N, mutex, 4);
                        testThread4sub3 = new Thread(r2);

                        Runnable r3 = new DFTMatrixMutexSync(a, b, N / 2, N / 2, N, N, N, mutex, 4);
                        testThread4sub4 = new Thread(r3);

                        testThread4sub1.start();
                        testThread4sub2.start();
                        testThread4sub3.start();
                        testThread4sub4.start();

                        messageTxt.append("4 Thread(Mutex)# A+B calculation and  DFT calculation.\n");

                        synchronized (mutex) {
                            while (mutex.availablePermits() < threadSize * 2) {
                                try {

                                    mutex.wait(); // (Releases lock, and reacquires on wakeup)
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }


                        Instant inst2 = Instant.now();
                        messageTxt.append("\n4 Thread(Mutex)# Elapsed Time: " + Duration.between(inst1, inst2).toString() + "\n");
                        start4T.setEnabled(true);
                        cancel4T.setEnabled(false);
                    }
                });
                testThread4.start();
                start4T.setEnabled(false);
                cancel4T.setEnabled(true);
            }
        });
        cancel4T.setEnabled(false);
        cancel4T.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                testThread4sub1.stop();
                testThread4sub2.stop();
                testThread4sub3.stop();
                testThread4sub4.stop();

                testThread4.stop();
                start4T.setEnabled(true);
                cancel4T.setEnabled(false);
                messageTxt.append("4 Thread(Mutex)# Cancel Operation\n");
            }
        });

        JButton exit = new JButton("Exit");

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        panel.add(startST);
        panel.add(cancelST);

        panel.add(start4T);
        panel.add(cancel4T);
        panel.add(exit);

        // Text Area at the Center
        messageTxt = new JTextArea(10, 25);
        messageTxt.setEditable(false);


        messageTxt.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                messageTxt.setCaretPosition(messageTxt.getDocument().getLength());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {

            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
        JScrollPane jScrollPane = new JScrollPane(messageTxt);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        //Adding Components to the frame.
        frame.getContentPane().add(BorderLayout.NORTH, panel);

        frame.getContentPane().add(BorderLayout.CENTER, jScrollPane);
        frame.setVisible(true);


        messageTxt.append("N size " + N + ". Because single thread takes too long on larger N numbers because my algorithm is bad and slow.");
        Q2test();
    }


    public static void Q2test() {
        AddressBook book = new AddressBook();
        book.add(new AddressBookPerson("Ali", "ali@veli.com"));
        book.add(new AddressBookPerson("Hüseyin", "huseyin@veli.com"));

        AddressBookGroup group = new AddressBookGroup("group@mail.com");

        group.add(new AddressBookPerson("muhammed", "muhammed@ozkan.com.tr"));
        group.add(new AddressBookPerson("naber", "nane@na.com"));

        AddressBookGroup subgroup = new AddressBookGroup("subgroup@group.com");
        subgroup.add(new AddressBookPerson("dene", "deneme@sad.com"));

        AddressBookGroup subsubgroup = new AddressBookGroup("subsubgroup@group.com");
        subsubgroup.add(new AddressBookPerson("dene", "deneme@sad.com"));

        subgroup.add(subsubgroup);


        group.add(subgroup);

        book.add(group);

        book.add(new AddressBookPerson("Muhammed", "test@test.com"));

        book.print();
    }


    public static void Q3ClassicSync4ThreadTest() {

        Thread testThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Complex.fillRandomComplex(a, 0, 0, N, N);

                messageTxt.append("A matris fill random\n");

                Complex.fillRandomComplex(b, 0, 0, N, N);
                messageTxt.append("B matris fill \n");


                List<String> sync = new ArrayList<String>();

                int threadSize = 4;

                Instant inst1 = Instant.now();

                Runnable r0 = new DFTMatrixClassicSync(a, b, 0, 0, N / 2, N / 2, N, sync, 4);
                new Thread(r0).start();

                Runnable r1 = new DFTMatrixClassicSync(a, b, 0, N / 2, N / 2, N, N, sync, 4);
                new Thread(r1).start();

                Runnable r2 = new DFTMatrixClassicSync(a, b, N / 2, 0, N, N / 2, N, sync, 4);
                new Thread(r2).start();

                Runnable r3 = new DFTMatrixClassicSync(a, b, N / 2, N / 2, N, N, N, sync, 4);
                new Thread(r3).start();


                messageTxt.append("a+b calc DFT\n");

                synchronized (sync) {
                    while (sync.size() < threadSize * 2) {
                        try {
                            sync.wait(); // (Releases lock, and reacquires on wakeup)
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }


                Instant inst2 = Instant.now();
                messageTxt.append("Elapsed Time: " + Duration.between(inst1, inst2).toString() + "\n");
            }
        });
        testThread.start();

    }
}
