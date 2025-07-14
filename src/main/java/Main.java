import exception.NotHansException;

import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        toConvert();
    }
    private static void toConvert() throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输出要被转化文件，支持路径");
        File path = new File(sc.next());
        System.out.println("是否追加一级简码？(y/n)");
        boolean isAddSimple = sc.next().toLowerCase().matches("y");
        if(path.isDirectory()) {
            // 路径，
            System.out.println("输入的为路径");
            for(File f:path.listFiles()) {
                if(f.isDirectory()) continue;
                parseFile(f, isAddSimple);
            }
        }else {
            try {
                parseFile(path, isAddSimple);
            }catch (FileNotFoundException e) {
                System.err.println("文件不存在！");
            }
        }

    }

    /**
     * 将一个文件的词语转化为五笔编码
     * 输入文件的格式必须为{{词语 词频 }}，中间用空白符分隔
     * 输出文件与输入文件同目录，名称为输入文件.cov，格式为{{词语\t五笔编码\t词频}}
     * @param f 输入文件
     * @param isAppendLvl1 是否在输出文件头部增加一级五笔简码
     * @throws IOException 发生IO错误
     */
    public static void parseFile(final File f, boolean isAppendLvl1) throws IOException {
        BufferedReader l1br = null;
        if(isAppendLvl1) l1br = new BufferedReader(new InputStreamReader(Main.class.getResourceAsStream("Level1Code")));

        final File INPUT_FILE = f;
        final File OUTPUT_PATH = new File(f.getAbsolutePath()+".cov");

        BufferedWriter bw = new BufferedWriter(new FileWriter(OUTPUT_PATH, true));
        BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE));

        // 写一级简码
        for(String buf;isAppendLvl1&&(buf=l1br.readLine())!=null;) {
            String[] tmp = buf.split("\\s");
            bw.write(tmp[0]+"\t"+tmp[1]+"\t");
            bw.newLine();
        }
        if(l1br != null) l1br.close();

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
