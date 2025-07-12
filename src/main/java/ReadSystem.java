import exception.NotHansException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class ReadSystem {
    private static ReadSystem instance;

    private final File topDir;
    private final HashMap<Character, char[]> hansMap;
    private ReadSystem() {
        topDir = new File(this.getClass().getResource("UnicodeCJK-WuBi/").getFile());
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
    public static String getFrontCode(char hans, int frontNum) {
        String full = getFullCode(hans);
        return full.substring(0,frontNum);
    }
    public static String getbackCode(char hans, int backNum) {
        String full = getFullCode(hans);
        return full.substring(full.length()-backNum);
    }
}
