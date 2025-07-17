import exception.NotHansException;

import java.io.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        toConvert();
    }
    private static void toConvert() throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输出要被转化文件，支持路径");
        File path = new File(sc.next());
//        File path = new File("/home/x0710/Desktop/priority.dict");
        System.out.println("是否追加一级简码？(y/n)");
        boolean isAddSimple = sc.next().toLowerCase().matches("y");
        if(path.isDirectory()) {
            // 路径，
            System.out.println("输入的为路径");
            for(File f: Objects.requireNonNull(path.listFiles())) {
                if(f.isDirectory()) continue;
                parseFile(f, isAddSimple, true);
            }
        }else {
            try {
                parseFile(path, isAddSimple, true);
            }catch (FileNotFoundException e) {
                System.err.println("文件不存在！");
            }
        }

    }

    /**
     * 将一个文件的词语转化为五笔编码
     * 输入文件的格式必须为{{词语 词频(DF) }}，中间用空白符分隔
     * 输出文件与输入文件同目录，名称为输入文件.cov，格式为{{词语\t五笔编码\t词频}}
     * @param INPUT_FILE 输入文件
     * @param isAppend 是否在输出文件头部增加一、二级五笔简码
     * @param simplizer 是否启用写入最简五笔，但在写入最简码后，也总是会写入一遍原码
     * @throws IOException 发生IO错误
     */
    public static void parseFile(final File INPUT_FILE, final boolean isAppend, final boolean simplizer) throws IOException {
        BufferedReader l1br = null;
        BufferedReader l2br = null;
        if(isAppend) {
            l1br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(Main.class.getResourceAsStream("Level1Code"))));
            l2br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(Main.class.getResourceAsStream("Level2Code"))));
        }

        final File OUTPUT_PATH = new File(INPUT_FILE.getAbsolutePath()+".cov");

        HashSet<String> writted = new HashSet<>();

        BufferedWriter bw = new BufferedWriter(new FileWriter(OUTPUT_PATH, false));
        BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE));

        if(isAppend) {
            // 写一级简码
            writeSth(l1br, writted, bw);

            // 写二级简码
            writeSth(l2br, writted, bw);
        }


        int count = 0;
        String hans; // 接下来要被转换成五笔编码的词组
        for(String buf;(buf=br.readLine())!=null;) {
            String[] split = buf.split("\\s");
            // split
            count++;
            hans = split[0];
            try {
                String wubicode = TransSystem.getWubiCode(hans),
                        raw = wubicode;
                StringBuilder wb = new StringBuilder(wubicode);
                while (simplizer) { // 尽力缩减首先进入转换词语的重叠，保证输出质量
                    if(writted.contains(wb.toString())) {
                        writted.add(wubicode);
                        bw.write(hans+"\t"+wubicode+"\t"+split[1]);
                        bw.newLine();
                        break;
                    }else {
                        wubicode = wb.toString();
                        wb.deleteCharAt(wb.length() - 1);
                    }
                }
                if(!wubicode.equals(raw)) { // 如果原码和简写码相同，就不再写，防止同码出现两行相同结果
                    bw.write(hans+"\t"+raw+"\t"+split[1]);
                    bw.newLine();
                }
            }
            catch (NotHansException e) {
                System.err.printf("提示：在第%d行，文本“%s”无法转换！\n", count, hans);
            }
        }
        bw.flush();
        bw.close();
        br.close();
        System.out.printf("文件%s成功转换了%d个词组\n", INPUT_FILE.getName(), count);

    }

    protected static void writeSth(BufferedReader l1br, HashSet<String> writted, BufferedWriter bw) throws IOException {
        for(String buf;(buf=l1br.readLine())!=null;) {
            String[] tmp = buf.split("\\s");
            bw.write(tmp[0]+"\t"+tmp[1]+"\t");
            writted.add(tmp[1]);
            bw.newLine();
        }
        l1br.close();
    }
}
