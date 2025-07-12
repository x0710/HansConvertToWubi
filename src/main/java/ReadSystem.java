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

    private final File topDir;
    private final HashMap<Character, char[]> hansMap;
    private ReadSystem() {
        topDir = new File(this.getClass().getResource("UnicodeCJK-WuBi/").getFile();
        hansMap = new HashMap<>(30000);
        try (BufferedReader br = new BufferedReader(new FileReader(new File(topDir, "CJK.txt")))) {
            for(String buf;(buf=br.readLine())!=null;) {
                String[] bufSplit = buf.split(",");
                hansMap.put(bufSplit[1].charAt(0), bufSplit[2].toCharArray());
            }
        }
        catch (IOException e) {
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
        if(instance == null) {
            synchronized (ReadSystem.class) {
                if(instance == null) {
                    instance = new ReadSystem();
                }
            }
        }
        if(hans < 0x4e00 || hans > 0x9fff) throw new NotHansException(hans);
        return String.valueOf(instance.hansMap.get((char)hans));
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
}
