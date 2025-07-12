import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class TransSystem {
    final private HashSet<String> datum;
    public TransSystem(File input) {
        datum = new HashSet<>();
        try(BufferedReader br = new BufferedReader(new FileReader(input))) {
            for(String buffer;(buffer=br.readLine())!=null;) {
                String[] bufSplit = buffer.split("\\s");
                datum.add(bufSplit[0]);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
    public HashSet<String> getDatum() {
        return new HashSet<>(datum);
    }
    public static String getWubiCode(String s) {
        if(s == null) return "";
        char[] sarr = s.toCharArray();
        switch (sarr.length) {
            case 0:return "";
            case 1:return ReadSystem.getFullCode(sarr[0]);
            case 2:return ReadSystem.getFrontCode(sarr[0], 2).concat(ReadSystem.getFrontCode(sarr[1], 2));
            case 3:return ReadSystem.getFrontCode(sarr[0], 1).concat(ReadSystem.getFrontCode(sarr[1], 1)).
                    concat(ReadSystem.getFrontCode(sarr[2], 2));
            case 4:return ReadSystem.getFrontCode(sarr[0], 1).concat(ReadSystem.getFrontCode(sarr[1], 1)).
                    concat(ReadSystem.getFrontCode(sarr[2], 1)).concat(ReadSystem.getFrontCode(sarr[3], 1));
            default:return ReadSystem.getFrontCode(sarr[0], 1).concat(ReadSystem.getFrontCode(sarr[1], 1)).
                    concat(ReadSystem.getFrontCode(sarr[2], 1)).concat(ReadSystem.getFrontCode(sarr[sarr.length-1], 1));
        }

    }
}
