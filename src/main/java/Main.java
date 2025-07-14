import exception.NotHansException;

import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.printf("请输出要被转化文件，支持路径\n");
        File path = new File(sc.next());
        if(path.isDirectory()) {
            // 路径，
            System.out.println("输入的为路径");
            for(File f:path.listFiles()) {
                if(f.isDirectory()) continue;
                parseFile(f);
            }
        }else {
            try {
                parseFile(path);
            }catch (FileNotFoundException e) {
                System.err.println("文件不存在！");
            }
        }


    }
    public static void parseFile(final File f) throws IOException {
        final File INPUT_FILE = f;
        final File OUTPUT_PATH = new File(f.getAbsolutePath()+"-Conver");

        BufferedWriter bw = new BufferedWriter(new FileWriter(OUTPUT_PATH, false));
        BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE));

        int count = 0;
        String hans = null; // 接下来要被转换成五笔编码的词组
        for(String buf;(buf=br.readLine())!=null;) {
            String[] split = buf.split("\\s");
            // split
            count++;
            hans = split[0];
            try {
                String wubiCode = TransSystem.getWubiCode(hans);
                bw.write(hans+"\t"+wubiCode+"\t"+split[1]);
                bw.newLine();
            }
            catch (NotHansException e) {
                System.err.printf("提示：在第%d行，文本“%s”无法转换！\n", count, hans);
            }
        }
        bw.flush();
        bw.close();
        br.close();
        System.out.printf("文件%s成功转换了%d个词组\n", f.getName(), count);

    }
}
