package calculator.view;

import camp.nextstep.edu.missionutils.Console;

public class IO {

    public String input() {
        System.out.println("덧셈할 문자열을 입력해 주세요.");
        String inputs = Console.readLine();
        if (inputs == null) {
            throw new IllegalArgumentException("입력을 읽을 수 없습니다.");
        }
        return inputs;
    }

    public void printResult(int result) {
        System.out.println("결과 : " + result);
    }
}
