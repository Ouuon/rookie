package dicy;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
public class dictionary {
    private static Map<String, String> dict = new TreeMap<>();
    private static JTextArea display;
    private static JTextField engField;
    private static JTextField meanField;
    private  static JLabel mean;
    private static JLabel eng;
    private static JLabel eng1;
    private static JLabel cn;
    private static JScrollPane scrollPane;
    private static JButton add;
    private static JButton edit;
    private static JButton search;
    private static JButton view;
    private static JButton del;
    private static JButton random;
    private static String currentAnswer;
    private static JButton nextBtn;

    public static void main(String[] args) {
        dict = loadDictionary();
        JFrame jf = new JFrame("英语词典");
        jf.setLayout(null);

        jf.setBounds(0, 0, 600, 400);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);


        engField = new JTextField(10);
        eng = new JLabel("单词");
        eng1 = new JLabel("英文");
        eng1.setBounds(160, 40, 50, 30);
        jf.add(eng1);
        eng1.setVisible(false);
        engField.setBounds(200, 40, 400, 30);
        eng.setBounds(160, 40, 50, 30);
        engField = new JTextField(10);
        engField.setBounds(200, 40, 400, 30);
        jf.add(engField);
        engField.addActionListener(e -> {
            if (add.isVisible()) {
                addWord();
            } else if (search.isVisible()) {
                searchWord();
            } else if (random.isVisible()) {
                checkAnswer();
            }
        });
        jf.add(eng);
        jf.add(engField);

        meanField = new JTextField(20);
        mean = new JLabel("解释");
        cn = new JLabel("中文");
        jf.add(cn);
        cn.setVisible(false);
        cn.setBounds(160, 80, 400, 30);
        meanField.setBounds(200, 80, 400, 30);
        mean.setBounds(160, 80, 400, 30);
        meanField.addActionListener(e -> {
            if (add.isVisible()) {
                addWord();
            } else if (edit.isVisible()) {
                editWord();
            } else if (search.isVisible()) {
                searchWord();
            }
        });
        jf.add(meanField);
        jf.add(mean);

        display = new JTextArea(15, 30);
        display.setEditable(false);
        display.setLineWrap(true);
        scrollPane = new JScrollPane(display);
        scrollPane.setBounds(200, 160, 380, 200);
        jf.add(scrollPane);

        JLabel submit = new JLabel("提交");
        submit.setBounds(160, 120, 50, 30);
        jf.add(submit);

        add = new JButton("添加单词");
        add.setBounds(200, 120, 120, 30);
        jf.add(add);
        add.setVisible(false);

        edit = new JButton("修改单词");
        edit.setBounds(200, 120, 120, 30);
        jf.add(edit);
        edit.setVisible(false);

        del = new JButton("删除单词");
        del.setBounds(200, 120, 120, 30);
        jf.add(del);
        del.setVisible(false);

        search = new JButton("查询单词");
        search.setBounds(200, 120, 120, 30);
        jf.add(search);
        search.setVisible(false);

        view = new JButton("浏览单词");
        view.setBounds(200, 120, 120, 30);
        jf.add(view);
        view.setVisible(false);

        JButton quizBtn = new JButton("拼写测试");
        quizBtn.setBounds(0, 280, 150, 40);
        quizBtn.addActionListener(e -> {
            allHide();
            allShow();
            nextBtn.setVisible(true);
            random.setVisible(true);  // 显示"下一个"按钮
            startQuiz();              // 开始测试
        });

        add.addActionListener(e -> addWord());
        edit.addActionListener(e -> editWord());
        del.addActionListener(e -> deleteWord());
        search.addActionListener(e -> searchWord());
        view.addActionListener(e -> viewAll());


        //侧边导航
        JButton addBtn = new JButton("添加单词");
        addBtn.setBounds(0, 30, 150, 40);
        addBtn.addActionListener(e -> {
            allHide();
            allShow();
            add.setVisible(true);
         });

        JButton editBtn = new JButton("修改单词");
        editBtn.setBounds(0, 80, 150, 40);
        editBtn.addActionListener(e -> {
            allHide();
            allShow();
            edit.setVisible(true);
        });

        JButton deleteBtn = new JButton("删除单词");
        deleteBtn.setBounds(0, 130, 150, 40);
        deleteBtn.addActionListener(e -> {
            allHide();
            allShow();
            mean.setVisible(false);
            meanField.setVisible(false);
            del.setVisible(true);
        });

        JButton searchBtn = new JButton("查询单词");
        searchBtn.setBounds(0, 180, 150, 40);
        searchBtn.addActionListener(e -> {
            allHide();
            allShow();
            mean.setVisible(false);
            eng.setVisible(false);
            eng1.setVisible(true);
            cn.setVisible(true);
            search.setVisible(true);
        });

        JButton viewBtn = new JButton("浏览全部");
        viewBtn.setBounds(0, 230, 150, 40);
        viewBtn.addActionListener(e -> {
            allHide();
            view.setVisible(true);
        });

        random = new JButton("检查答案");
        random.setBounds(200, 120, 120, 30);
        jf.add(random);
        random.setVisible(false);
        random.addActionListener(e -> checkAnswer());

        nextBtn = new JButton("下一个");
        nextBtn.setBounds(330, 120, 120, 30);
        jf.add(nextBtn);
        nextBtn.setVisible(false);
        nextBtn.addActionListener(e -> nextQuiz());

        jf.add(addBtn);
        jf.add(editBtn);
        jf.add(deleteBtn);
        jf.add(searchBtn);
        jf.add(viewBtn);
        jf.add(quizBtn);
        jf.setVisible(true);
        if (!new File("dictionary.txt").exists()) {
            saveDictionary();  // 创建文件
        }
    }
    private static void allShow(){
        eng.setVisible(true);
        engField.setVisible(true);
        mean.setVisible(true);
        meanField.setVisible(true);

        engField.setText("");
        meanField.setText("");
        display.setText("");
    }
    private static void allHide(){
        eng.setVisible(false);
        engField.setVisible(false);
        mean.setVisible(false);
        meanField.setVisible(false);
        add.setVisible(false);
        edit.setVisible(false);
        del.setVisible(false);
        search.setVisible(false);
        view.setVisible(false);
        random.setVisible(false);
        nextBtn.setVisible(false);
        cn.setVisible(false);
        eng1.setVisible(false);

        engField.setEditable(true);   // 恢复可编辑
        meanField.setEditable(true);  // 恢复可编辑

        engField.setText("");
        meanField.setText("");
        display.setText("");
    }
    private static void startQuiz() {
        if (dict.isEmpty()) {
            display.setText("词库为空");
            return;
        }

        String[] keys = dict.keySet().toArray(new String[0]);
        currentAnswer = keys[new Random().nextInt(keys.length)];

        // 显示中文意思
        mean.setText("中文");
        meanField.setText(dict.get(currentAnswer));
        meanField.setEditable(false);  // 中文只读

        // 清空英文输入框
        engField.setText("");
        engField.setEditable(true);  // 英文可输入

        display.setText("请根据中文输入英文单词：\n" + dict.get(currentAnswer));
    }
    private static void checkAnswer() {
        String userAnswer = engField.getText().trim();

        if (userAnswer.isEmpty()) {
            display.setText("请输入英文单词");
            return;
        }

        if (userAnswer.equals(currentAnswer)) {
            display.setText("Good job!");
        } else {
            display.setText("Try again!");
        }
    }

    // 下一个单词
    private static void nextQuiz() {
        startQuiz();  // 重新随机
    }

    // 增加单词
    private static void addWord() {
        String word = engField.getText().trim();
        String meaning = meanField.getText().trim();

        if (!isValidInput(word, meaning)) {
            return;
        }

        if (dict.containsKey(word)) {
            display.setText("单词 \"" + word + "\" 已存在！");
            return;
        }

        dict.put(word, meaning);
        display.setText("增加成功！\n" + word + " : " + meaning);
        clearFields();
        saveDictionary();
    }

    // 修改单词
    private static void editWord() {
        String word = engField.getText().trim();
        String meaning = meanField.getText().trim();

        if (!isValidInput(word, meaning)) {
            return;
        }

        // 检查单词是否存在
        if (!dict.containsKey(word)) {
            display.setText("单词 \"" + word + "\" 不存在！");
            return;
        }

        dict.put(word, meaning);
        display.setText("修改成功！\n" + word + " : " + meaning);
        clearFields();
        saveDictionary();
    }
    // 清空输入框（可以继续提取）
    private static void clearFields() {
        engField.setText("");
        meanField.setText("");
        engField.requestFocus();
    }
    // 验证输入是否合法
    private static boolean isValidInput(String word, String meaning) {
        if (word.isEmpty() || meaning.isEmpty()) {
            display.setText("单词和解释都不能为空！");
            return false;
        }

        if (word.contains("=") || word.contains("\n") ||
                meaning.contains("=") || meaning.contains("\n")) {
            display.setText("不能包含=或换行符");
            return false;
        }

        if (word.contains(" ")) {
            display.setText("单词不能包含空格");
            return false;
        }

        if (word.length() > 50) {
            display.setText("单词太长（最多50个字符）");
            return false;
        }

        return true;
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
        String engInput = engField.getText().trim();
        String meanInput = meanField.getText().trim();

        // 两个都为空
        if (engInput.isEmpty() && meanInput.isEmpty()) {
            display.setText("请输入英文或中文");
            return;
        }

        // 优先查英文
        if (!engInput.isEmpty()) {
            String meaning = dict.get(engInput);
            if (meaning != null) {
                display.setText(engInput + " : " + meaning);
                return;
            } else {
                display.setText("未找到单词: " + engInput);
                return;
            }
        }

        // 查中文
            StringBuilder sb = new StringBuilder("包含\"" + meanInput + "\"的单词：\n");
            boolean found = false;
            for (Map.Entry<String, String> entry : dict.entrySet()) {
                if (entry.getValue().contains(meanInput)) {
                    sb.append(entry.getKey()).append(" : ").append(entry.getValue()).append("\n");
                    found = true;
                }
            if (found) {
                display.setText(sb.toString());
            } else {
                display.setText("未找到：" + meanInput);
            }
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
    //加载
    private static Map<String, String> loadDictionary() {
        Map<String, String> map = new TreeMap<>();
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
            JOptionPane.showMessageDialog(null, "加载词典失败：" + e.getMessage());
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
            JOptionPane.showMessageDialog(null, "保存失败：" + e.getMessage());
        }
    }
}

