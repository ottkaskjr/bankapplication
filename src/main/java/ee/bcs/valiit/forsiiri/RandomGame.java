package ee.bcs.valiit.forsiiri;

import java.math.BigDecimal;

public class RandomGame {
    private BigDecimal id;
    private int answer;
    private int attempts;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }
}
