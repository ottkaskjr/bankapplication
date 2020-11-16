package ee.bcs.valiit.lessons;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Lesson1MathUtilTest {

    // SIIN SAAB TESTIDA KOODIJUPPE!!
    @Test
    public void min(){
        assertEquals(3, Lesson1MathUtil.min(2, 3));
    }

    @Test
    public void max(){
        assertEquals(3, Lesson1MathUtil.max(2,3));
    }

    @Test
    public void abs(){
        assertEquals(3, Lesson1MathUtil.abs(3));
    }

    @Test
    public void isEven(){
        assertEquals(true, Lesson1MathUtil.isEven(20));
    }
    @Test
    public void compareDoubles(){
        double a = 1.0;
        double b = 1.000001;
        // VÕRDLEB KAHTE ARVU AINULT JUHUL, KUI NENDE VAHE ON SUUREM KUI 0.000001
        assertEquals(a, b, 0.00001);
    }

    @Test
    public void min3(){
        assertEquals(3, Lesson1MathUtil.min(1, 2, 3));
    }

    @Test
    public void max3(){
        assertEquals(3, Lesson1MathUtil.max(1, 2, 3));
    }
}
