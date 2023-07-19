import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import static org.apache.commons.collections4.ListUtils.partition;

public class UI {
    static List<TreeNode> treeNodes;
    static String[] textString;

    public static void createWindow() {
        JFrame frame = new JFrame("Translator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createUI(frame);
        frame.setSize(300, 300);
        frame.setMaximumSize(frame.getSize());
        frame.setMinimumSize(frame.getSize());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void createUI(final JFrame frame) {
        JPanel panel = new JPanel();
        LayoutManager layout = new FlowLayout();
        panel.setLayout(layout);
        JButton open = new JButton("OpenFile");
        JButton about = new JButton("About");
        JButton exit = new JButton("Exit");
        SpringLayout translator = new SpringLayout();
        panel.setLayout(translator);
        open.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser("./");
            int option = fileChooser.showOpenDialog(frame);
            if (option == JFileChooser.CANCEL_OPTION)
                return;
            if (option == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                java.util.List<String[]> csvLists = null;
                try {
                    csvLists = CsvParser.parse(file.getAbsolutePath());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

                // remove csv headers
                csvLists.remove(0);


                java.util.List<java.util.List<String[]>> parts = partition(csvLists, 200);
                TimeMeasure timeMeasureTree = new TimeMeasure("Tree Construction");
                timeMeasureTree.start();
                treeNodes = parts.stream().parallel().map(
                        listOfStrings -> {
                            List<Node> nodes = listOfStrings.stream().map(
                                    (String[] strings) -> new Node(strings)
                            ).collect(Collectors.toList());
                            MatrixMaker matrixMaker = new MatrixMaker(nodes);
                            int[][] keys = matrixMaker.keyMaker();
                            Tree tree = new Tree(keys, 0, nodes.size(), nodes);
                            return tree.treeMaker(keys, 0, nodes.size());
                        }
                ).collect(Collectors.toList());
                timeMeasureTree.end();


            }
            JFrame jframe = new JFrame();
            jframe.setSize(300, 300);
            jframe.setVisible(true);
            JPanel jPanel = new JPanel();
            JButton showTree = new JButton("Show tree");
            JButton dictionary = new JButton("Dictionary");
            SpringLayout springLayout = new SpringLayout();
            jPanel.setLayout(springLayout);
            springLayout.putConstraint(SpringLayout.WEST, showTree, 95, SpringLayout.WEST, jPanel);
            springLayout.putConstraint(SpringLayout.NORTH, showTree, 50, SpringLayout.NORTH, jPanel);
            springLayout.putConstraint(SpringLayout.WEST, dictionary, 95, SpringLayout.WEST, jPanel);
            springLayout.putConstraint(SpringLayout.NORTH, dictionary, 40, SpringLayout.NORTH, showTree);
            jPanel.add(showTree);
            jPanel.add(dictionary);
            jframe.getContentPane().add(jPanel, BorderLayout.CENTER);
            showTree.addActionListener(e1 -> {
                //drawGraphicTree
                TreeDrawer.draw(treeNodes.get(treeNodes.size()/2));

            });
            dictionary.addActionListener(e1 -> { ///dictionary button
                JFrame jFrame = new JFrame();
                jFrame.setSize(500, 200);
                jFrame.setVisible(true);
                JPanel jPanel1 = new JPanel();
                SpringLayout dictionaryLayout = new SpringLayout();
                JLabel textLabel = new JLabel("Enter text: ");
                JTextArea text = new JTextArea(5, 10);
                JLabel translate = new JLabel();
                JButton translateButton = new JButton("Translate");
                jPanel1.setLayout(dictionaryLayout);
                jPanel1.add(textLabel);
                jPanel1.add(text);
                jPanel1.add(translate);
                jPanel1.add(translateButton);
                dictionaryLayout.putConstraint(SpringLayout.WEST, textLabel, 10, SpringLayout.WEST, jPanel1);
                dictionaryLayout.putConstraint(SpringLayout.NORTH, textLabel, 10, SpringLayout.NORTH, jPanel1);
                dictionaryLayout.putConstraint(SpringLayout.WEST, text, 10, SpringLayout.EAST, textLabel);
                dictionaryLayout.putConstraint(SpringLayout.NORTH, text, 10, SpringLayout.NORTH, jPanel1);
                dictionaryLayout.putConstraint(SpringLayout.WEST, translate, 80, SpringLayout.EAST, text);
                dictionaryLayout.putConstraint(SpringLayout.NORTH, translate, 10, SpringLayout.NORTH, jPanel1);
                dictionaryLayout.putConstraint(SpringLayout.WEST, translateButton, 170, SpringLayout.WEST, jPanel1);
                dictionaryLayout.putConstraint(SpringLayout.NORTH, translateButton, 100, SpringLayout.NORTH, jPanel1);
                jFrame.getContentPane().add(jPanel1);
                translateButton.addActionListener(e2 -> { ////translator button -> translate the words
                    AtomicReference<String> meaning = new AtomicReference<>("?");
                    textString = text.getText().split(" ");
                    String translatedText="";
                    for (String s : textString) {
                        TimeMeasure timeMeasureSearch = new TimeMeasure("Search");
                        timeMeasureSearch.start();
                        String word = s;

                        treeNodes.stream().forEach(
                                treeNode -> {
                                    if (!meaning.get().equals("?")) {
                                        return;
                                    }
                                    Search search = new Search(treeNode);
                                    String result = search.search(word);
                                    if (!result.equals("?")) {
                                        meaning.set(result);
                                    }
                                }
                        );
                        translatedText+=meaning.get()+" ";
                        meaning.set("?");
                        timeMeasureSearch.end();
                        translate.setText(translatedText);
                    }

                });
            });
        });
        about.addActionListener(e -> { ///about button
            JFrame jFrame = new JFrame();
            JLabel aboutLabel = new JLabel("<Html>Welcome to Translator! (◕‿◕✿)<br>This app can Translate English to Persian<br>By:" +
                    " Sheyda Safi Allah<br>Nazanin AryanMatin<br>Sargol Moslemi<br>❤</html>", SwingConstants.CENTER);
            jFrame.add(aboutLabel);
            jFrame.setSize(500, 500);
            jFrame.setVisible(true);
        });
        exit.addActionListener(e -> frame.dispose()); ///exit button
        translator.putConstraint(SpringLayout.WEST, open, 100, SpringLayout.WEST, panel);
        translator.putConstraint(SpringLayout.NORTH, open, 50, SpringLayout.NORTH, panel);
        translator.putConstraint(SpringLayout.WEST, about, 110, SpringLayout.WEST, panel);
        translator.putConstraint(SpringLayout.NORTH, about, 40, SpringLayout.NORTH, open);
        translator.putConstraint(SpringLayout.WEST, exit, 115, SpringLayout.WEST, panel);
        translator.putConstraint(SpringLayout.NORTH, exit, 40, SpringLayout.NORTH, about);
        panel.add(open);
        panel.add(about);
        panel.add(exit);
        frame.getContentPane().add(panel, BorderLayout.CENTER);
    }
}
