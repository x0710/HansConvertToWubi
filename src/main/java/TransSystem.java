public class TransSystem {
    /**
     * 获得一个词组的五笔编码
     * @param s 目标词组
     * @return 期望值
     */
    public static String getWubiCode(String s) {
        if(s == null) return "";
        char[] arr = s.toCharArray();
        switch (arr.length) {
            case 0:return "";
            case 1:return ReadSystem.getFullCode(arr[0]);
            case 2:return ReadSystem.getFrontCode(arr[0], 2).concat(ReadSystem.getFrontCode(arr[1], 2));
            case 3:return ReadSystem.getFrontCode(arr[0], 1).concat(ReadSystem.getFrontCode(arr[1], 1)).
                    concat(ReadSystem.getFrontCode(arr[2], 2));
            case 4:return ReadSystem.getFrontCode(arr[0], 1).concat(ReadSystem.getFrontCode(arr[1], 1)).
                    concat(ReadSystem.getFrontCode(arr[2], 1)).concat(ReadSystem.getFrontCode(arr[3], 1));
            default:return ReadSystem.getFrontCode(arr[0], 1).concat(ReadSystem.getFrontCode(arr[1], 1)).
                    concat(ReadSystem.getFrontCode(arr[2], 1)).concat(ReadSystem.getFrontCode(arr[arr.length-1], 1));
        }

    }
}
