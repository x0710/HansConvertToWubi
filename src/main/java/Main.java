import exception.NotHansException;

import java.io.*;

public class Main {
    public final static File OUTPUT_PATH = new File(System.getProperty("user.home")+"/Desktop/");
    public final static File INPUT_FILE = new File(System.getProperty("user.home")+"/Desktop/HansToWubi/src/main/resources/chinese-frequency-word-list/xiandaihaiyuchangyongcibiao.txt");
    public static void main(String[] args) throws IOException {

        BufferedWriter bw = new BufferedWriter(new FileWriter(new File(OUTPUT_PATH, INPUT_FILE.getName().concat("-Wubi")), false));
//        File transed = new File(Main.class.getResource("UnicodeCJK-WuBi/").getFile());
        BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE));

        int count = 0;
        String hans = null; // 接下来要被转换成五笔编码的词组
        for(String buf;(buf=br.readLine())!=null;) {
            String[] split = buf.split("\\s");
            hans = split[0];
            try {
                String wubiCode = TransSystem.getWubiCode(hans);
                bw.write(hans+"\t"+wubiCode+"\t");
                bw.newLine();
                count++;
            }
            catch (NotHansException e) {
                System.err.printf("提示：在第%d行，文本%s无法转换！\n", count, hans);
            }
        }
        bw.flush();
        bw.close();
        br.close();
        System.out.printf("成功转换了%d个词组", count);

    }
}
