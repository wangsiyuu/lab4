package exp1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;



public class Main {
  private static Scanner cin;

  public static void main(String[] args) throws FileNotFoundException {
    FileInputStream fis = new FileInputStream("out11.txt");
    System.setIn(fis);
    cin = new Scanner(System.in);
    String cmd;
    Expression ex = new Expression();
    while (cin.hasNext()) {
      cmd = cin.nextLine();
      cmd = cmd.toLowerCase();
      //System.out.println(cmd);
      // get command
      if (cmd.charAt(0) == '!') {
        // simplify
        if (cmd.charAt(1) == 's') {
          String[] tmp = cmd.split("\\s");

          if (!tmp[0].equals("!simplify")) {
            System.out.println("Input Error!!\nNo such operation!!");
            System.exit(0);
            //continue;
          }
          tmp = tmp[1].split(",");
          for (int i = 0; i < tmp.length; i++) {
            tmp[i] = tmp[i].replace(" ", "");
            String[] tosimp = tmp[i].split("=");
            if (tosimp.length != 2 
                || tosimp[0].length() != 1 
                || (!(tosimp[0].charAt(0) >= 'a' && tosimp[0].charAt(0) <= 'z'))) {
              System.out.println("Input Error!!\nNo such operation!!");
              System.exit(0);
            }
            char tmpchar = tosimp[0].charAt(0);
            int dig = 1;
            try {
              dig = Integer.parseInt(tosimp[1]);
            } catch (Exception err) {
              System.out.println("Input Error");
              System.exit(0);
            }
            ex.simplify(tmpchar, dig);
            ex.merge();
          }
        } else if (cmd.charAt(1) == 'd') { // d/dy
          if (cmd.length() != 5 || !cmd.substring(1, 4).equals("d/d")) {
            System.out.println("Input Error!!\nNo such operation!!");
            System.exit(0);
          }
          char cmdchar = cmd.charAt(4);
          if (!(cmdchar >= 'a' && cmdchar <= 'z')) {
            System.out.println("Input Error!!\nNo such operation!!");
            System.exit(0);
          }
          ex.deri(cmdchar);
          ex.merge();
        } else {
          System.out.println("Input Error!!\nNo such operation!!");
        }
        ex.show();
      } else {      // get expression
        if (!ex.init(cmd)) {
          System.out.println("Input Error!!");
          System.exit(0);
          //continue;
        }
        ex.adjust();
        // ex.show();
        ex.merge();
        ex.show();
      }
    }
    System.exit(0);
  }
}
