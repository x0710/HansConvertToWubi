import exception.NotHansException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class ReadSystem {
    private final File topDir;
    private final HashMap<Character, char[]> hansMap;
    public ReadSystem() {
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
    public String getChat(char hans) throws NotHansException {
        if(hans < 0x4e00 || hans > 0x9fff) throw new NotHansException(hans);
        return String.valueOf(hansMap.get((char)hans));
    }
}
