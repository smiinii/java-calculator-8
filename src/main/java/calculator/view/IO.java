package calculator.view;

import camp.nextstep.edu.missionutils.Console;

public class IO {

    public String input() {
        return Console.readLine();
    }

    public void printResult(int result) {
        System.out.println(result);
    }
}
