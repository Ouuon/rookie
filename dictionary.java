package dicy;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
public class dictionary {
    private static Map<String, String> dict = new HashMap<>();
    private static JTextArea display;
    private static JTextField engField;
    private static JTextField meanField;

    public static void main(String[] args) {
        dict = loadDictionary();
        JFrame jf = new JFrame("英语词典");
        jf.setLayout(null);

        jf.setBounds(0,0,600,400);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JButton add = new JButton("增加单词");
        JButton edit = new JButton("修改单词");
        JButton del = new JButton("删除单词");
        JButton search = new JButton("查询单词");
        JButton view = new JButton("浏览全部单词");
        JButton random = new JButton("随机一个单词");
        JButton[] arr = {add,edit,del,search,view,random};
        for (int i = 0; i < arr.length; i++) {
            arr[i].setBounds(0,i * 60,120,60);
        }
        engField = new JTextField(10);
        JLabel eng = new JLabel("单词");
        engField.setBounds(160,20,400,30);
        eng.setBounds(130,20,50,30);

        meanField = new JTextField(20);
        JLabel mean = new JLabel("解释");
        meanField.setBounds(160,60,400,30);
        mean.setBounds(130,60,50,30);

        display = new JTextArea(15, 30);
        display.setEditable(false);
        display.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(display);
        scrollPane.setBounds(130, 100, 400, 200);

        add.addActionListener(e -> addWord());
        edit.addActionListener(e -> editWord());
        del.addActionListener(e -> deleteWord());
        search.addActionListener(e -> searchWord());
        view.addActionListener(e -> viewAll());
        random.addActionListener(e -> randomWord());

        jf.add(add);jf.add(edit);jf.add(del);jf.add(search);jf.add(view);jf.add(random);jf.add(engField);jf.add(meanField);jf.add(eng);jf.add(scrollPane);jf.add(mean);
        jf.setVisible(true);
        saveDictionary();
    }
    // 增加单词
    private static void addWord() {
        String word = engField.getText().trim();
        String meaning = meanField.getText().trim();

        if (word.isEmpty() || meaning.isEmpty()) {
            display.setText("单词和解释都不能为空！");
            return;
        }

        if (dict.containsKey(word)) {
            display.setText("单词 \"" + word + "\" 已存在！");
            return;
        }

        dict.put(word, meaning);
        display.setText("增加成功！\n" + word + " : " + meaning);
        engField.setText("");
        meanField.setText("");
        engField.requestFocus();
        saveDictionary();
    }
    // 修改单词
    private static void editWord() {
        String word = engField.getText().trim();
        String meaning = meanField.getText().trim();

        if (word.isEmpty() || meaning.isEmpty()) {
            display.setText("请输入要修改的单词和新的解释");
            return;
        }

        if (!dict.containsKey(word)) {
            display.setText("单词 \"" + word + "\" 不存在！");
            return;
        }

        dict.put(word, meaning);
        display.setText("修改成功！\n" + word + " : " + meaning);
        engField.setText("");
        meanField.setText("");
        saveDictionary();
    }
    // 删除单词
    private static void deleteWord() {
        String word = engField.getText().trim();

        if (word.isEmpty()) {
            display.setText("请输入要删除的单词");
            return;
        }

        if (!dict.containsKey(word)) {
            display.setText("单词 \"" + word + "\" 不存在！");
            return;
        }

        dict.remove(word);
        display.setText("删除成功！\n" + word + " 已删除");
        engField.setText("");
        meanField.setText("");
        saveDictionary();
    }
    // 查询单词
    private static void searchWord() {
        String word = engField.getText().trim();

        if (word.isEmpty()) {
            display.setText("请输入要查询的单词");
            return;
        }

        String meaning = dict.get(word);
        if (meaning != null) {
            display.setText(word + " : " + meaning);
        } else {
            display.setText("未找到单词: " + word);
        }
    }
    // 浏览全部
    private static void viewAll() {
        if (dict.isEmpty()) {
            display.setText("词库为空");
            return;
        }

        StringBuilder sb = new StringBuilder("全部单词：\n");
        for (Map.Entry<String, String> entry : dict.entrySet()) {
            sb.append(entry.getKey()).append(" : ").append(entry.getValue()).append("\n");
        }
        display.setText(sb.toString());
    }
    // 随机一个单词
    private static void randomWord() {
        if (dict.isEmpty()) {
            display.setText("词库为空");
            return;
        }
        String[] keys = dict.keySet().toArray(new String[0]);
        String word = keys[new Random().nextInt(keys.length)];
        String meaning = dict.get(word);
        engField.setText(word);
        meanField.setText(meaning);
        display.setText("已随机选取：\n" + word + " : " + meaning);
    }
    //加载
    private static Map<String, String> loadDictionary() {
        Map<String, String> map = new HashMap<>();
        File file = new File("dictionary.txt");

        if (!file.exists()) {
            return map;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    map.put(parts[0], parts[1]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
//保存
    private static void saveDictionary() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("dictionary.txt"))) {
            for (Map.Entry<String, String> entry : dict.entrySet()) {
                writer.write(entry.getKey() + "=" + entry.getValue());
                writer.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
