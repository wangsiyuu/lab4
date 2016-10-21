package exp1;

import java.util.ArrayList;

public class Expression {
  
  ArrayList<Node> an;

  boolean isch(char ch) {
    return ch >= 'a' && ch <= 'z';
  }
  
  boolean isdig(char ch) {
    return ch >= '0' && ch <= '9';
  }
  
  boolean init(String str) {
    str = str.replace(" ", "");
    // empty string
    if (str.length() < 1) {
      return false;
    }

    for (int i = 0; i < str.length(); i++) {
      //System.out.println(i+"  "+str.charAt(i));
      if (str.charAt(i) == '^') {
        if (i == 0) {
          return false;
        }
        if (!isch(str.charAt(i - 1))) {
          return false;
        }
        if (i + 1 >= str.length()) {
          return false;
        }
        if (!isdig(str.charAt(i + 1))) {
          return false;
        }
      } else if (str.charAt(i) == '+' || str.charAt(i) == '-') {
        if (i - 1 >= 0 && !isch(str.charAt(i - 1)) && !isdig(str.charAt(i - 1))) {
          return false;
        }
        if (i + 1 >= str.length()) {
          return false;
        }
        if (!isch(str.charAt(i + 1)) && !isdig(str.charAt(i + 1))) {
          return false;
        }
      } else if (str.charAt(i) == '*') {
        if (i - 1 < 0 || i + 1 >= str.length()) {
          return false;
        }
        if (!isch(str.charAt(i - 1)) && !isdig(str.charAt(i - 1))) {
          return false;
        }
        if (!isch(str.charAt(i + 1)) && !isdig(str.charAt(i + 1))) {
          return false;
        }
      } else if (isch(str.charAt(i))) {
        if (i - 1 >= 0) {
          char ch = str.charAt(i - 1);
          if (ch != '+' && ch != '-' && ch != '*' && ch != '^') {
            return false;
          }
        }
        if (i + 1 < str.length()) {
          char ch = str.charAt(i + 1);
          if (ch != '+' && ch != '-' && ch != '*' && ch != '^') {
            return false;
          }
        }
      } else if (isdig(str.charAt(i))) {
        if (i - 1 >= 0 && isch(str.charAt(i - 1))) {
          return false;
        }
      } else {
        return false;
      }
    }


    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < str.length(); i++) {
      if (str.charAt(i) == '-') {
        sb.append('+');
        sb.append(str.charAt(i));
      } else if (str.charAt(i) == '^') {
        StringBuilder sbb = new StringBuilder();
        for (int j = i + 1; j < str.length(); j++) {
          char ch = str.charAt(j);
          if (ch >= '0' && ch <= '9') {
            sbb.append(ch);
            i++;
          } else {
            break;
          }
        }
        int num = Integer.valueOf(sbb.toString());
        char ch = sb.charAt(sb.length() - 1);
        for (int j = 1; j < num; j++) {
          sb.append('*');
          sb.append(ch);
        }
      } else {
        sb.append(str.charAt(i));
      }
    }
    str = sb.toString();
    an = new ArrayList<Node>();
    String[] tmp = str.split("\\+");
    for (int i = 0; i < tmp.length; i++) {
      an.add(new Node(tmp[i]));
    }
    return true;
  }

  void show() {
    for (int i = 0; i < an.size(); i++) {
      if (i != 0 && an.get(i).var >= 0) {
        System.out.print("+");
      }
      an.get(i).showNode1();
    }

    if (an.size() == 0) {
      System.out.print("0");
    }
    System.out.println();
  }

  void adjust() {
    for (int i = 0; i < an.size(); i++) {
      an.get(i).adjust();
    }
  }

  void merge() {
    ArrayList<Node> atmp = new ArrayList<Node>();
    Node nd1;
    Node nd2;
    for (int i = 0; i < an.size(); i++) {
      nd1 = an.get(i);
      for (int j = i + 1; j < an.size(); j++) {
        nd2 = an.get(j);
        if (nd2.str.compareTo(nd1.str) == 0) {
          nd1.var += nd2.var;
          nd2.var = 0;
          an.set(j, nd2);
        }
      }
      if (nd1.var != 0) {
        atmp.add(nd1);
      }
    }
    an = atmp;
  }

  void simplify(char ch, int dig) {
    ArrayList<Node> tmp = new ArrayList<Node>();
    for (int i = 0; i < an.size(); i++) {
      tmp.add(an.get(i).simplify(ch, dig));
    }
    an = tmp;
  }

  void deri(char ch) {
    ArrayList<Node> tmp = new ArrayList<Node>();
    for (int i = 0; i < an.size(); i++) {
      tmp.add(an.get(i).deri(ch));
    }
    an = tmp;
  }
}
