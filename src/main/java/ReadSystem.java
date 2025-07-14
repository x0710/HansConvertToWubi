import exception.NotHansException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * 读取收到的字符并将其转为五笔编码的类
 */
public class ReadSystem {
    private static ReadSystem instance;

    // 存储五笔编码
    private final HashMap<Character, char[]> hansHashMap;
    // 存储词频
    private final HashMap<String, Integer> hansPriority;

    static {
        instance = new ReadSystem();
    }

    private ReadSystem() {
        hansPriority = new HashMap<>(35000);
        hansHashMap = new HashMap<>(21000);

        // 加载对照表
        File wubiRawcode = new File(this.getClass().getResource("UnicodeCJK-WuBi/").getFile());
        try (BufferedReader br = new BufferedReader(new FileReader(new File(wubiRawcode, "CJK.txt")))) {
            for(String buf;(buf=br.readLine())!=null;) {
                String[] bufSplit = buf.split(",");
                hansHashMap.put(bufSplit[1].charAt(0), bufSplit[2].toCharArray());
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        // 加载词频文件
        File priorityFile = new File(this.getClass().getResource("priority.dict").getFile());
        try (BufferedReader br1 = new BufferedReader(new FileReader(priorityFile))) {
            for(String buf;(buf=br1.readLine())!=null;) {
                String[] bufSplit = buf.split("\\s");
                hansPriority.put(bufSplit[0], Integer.parseInt(bufSplit[1]));
            }
        }catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * 获得到某个汉字的完整五笔编码
     * 注意，仅对U+4E00到U+9FFF的字符集合，不在此区间的字体会抛出异常
     * @param hans 要获得编码的汉字
     * @return 该汉字的五笔完整编码
     * @throws NotHansException 若该字不为汉字，或不在U+4E00到U+9FFF区间
     */
    public static String getFullCode(char hans) throws NotHansException {
        if(hans < 0x4e00 || hans > 0x9fff) throw new NotHansException(hans);
        return String.valueOf(instance.hansHashMap.get((char)hans));
    }

    /**
     * 获得某个汉字五笔编码的前某位
     * @param hans 目标汉字
     * @param frontNum 前 {frontNum}位
     * @return 目的编码
     * @throws NotHansException 若该字不为汉字，或不在U+4E00到U+9FFF区间
     */
    public static String getFrontCode(char hans, int frontNum) throws NotHansException{
        String full = getFullCode(hans);
        return full.substring(0,frontNum);
    }
    /**
     * 获得某个汉字五笔编码的后某位
     * @param hans 目标汉字
     * @param backNum 后 {backNum}位
     * @return 目的编码
     * @throws NotHansException 若该字不为汉字，或不在U+4E00到U+9FFF区间
     */
    public static String getbackCode(char hans, int backNum) throws NotHansException{
        String full = getFullCode(hans);
        return full.substring(full.length()-backNum);
    }

    /**
     * 获得某个词汇的词频
     * @param val 目标汉字
     * @return 汉字词频，如果没有结果返回0
     */
    public static int getPriority(String val) {
        try {
            return instance.hansPriority.get(val);
        }catch (NullPointerException e) {
            return 0;
        }
    }

}
