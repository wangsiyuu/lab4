package exp1;

//Node : 保存每一个项
//Node.d 系数
//Node.str 未知数 ： string

public class Node {
  String str;
  int var;

  Node(String str) {
    StringBuilder sb = new StringBuilder();
    var = 1;
    if (str.charAt(0) == '-') {
      var = -1;
      str = str.substring(1);
    }
    String[] tmp = str.split("\\*");
    // tmp[].charAt()是未知数前面的系数
    for (int i = 0; i < tmp.length; i++) {
      if (tmp[i].charAt(0) <= '9' && tmp[i].charAt(0) >= '0') {
        var = var * Integer.valueOf(tmp[i]);
      } else {
        sb.append(tmp[i].charAt(0));
      }
    }
    this.str = sb.toString();
  }

  void adjust() {
    int[] cnt = new int[26];
    for (int i = 0; i < str.length(); i++) {
      cnt[str.charAt(i) - 'a']++;
    }
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < 26; i++) {
      for (int j = 0; j < cnt[i]; j++) {
        sb.append((char) (i + 'a'));
      }
    }
    str = sb.toString();
  }

  Node simplify(char ch, int dig) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < str.length(); i++) {
      if (str.charAt(i) == ch) {
        var = var * dig;
      } else {
        sb.append(str.charAt(i));
      }
    }
    str = sb.toString();
    return this;
  }

  Node deri(char ch) {
    StringBuilder sb = new StringBuilder();
    int num = 0;
    for (int i = 0; i < str.length(); i++) {
      if (str.charAt(i) != ch) {
        sb.append(str.charAt(i));
      } else {
        if (num > 0) {
          sb.append(str.charAt(i));
        }
        num++;
      }
    }
    var = num * var;
    str = sb.toString();
    return this;
  }

  void showNode() {
    System.out.print(var);
    for (int i = 0; i < str.length(); i++) {
      System.out.print("*" + str.charAt(i));
    }
  }

  // *TODO*
  // change plain syso to String
  void showNode1() {
    System.out.print(var);
    char pchar = '\0';
    int cnt = 0;
    for (int i = 0; i < str.length(); i++) {
      if (str.charAt(i) != pchar) {
        if (cnt == 1) {
          System.out.print("*" + pchar);
        } else if (cnt > 1) {
          System.out.print("*" + pchar + "^" + cnt);
        }
        pchar = str.charAt(i);
        cnt = 1;
      } else {
        cnt++;
      }
    }
    if (cnt == 1) {
      System.out.print("*" + pchar);
    } else if (cnt > 1) {
      System.out.print("*" + pchar + "^" + cnt);
    }
  }
}
