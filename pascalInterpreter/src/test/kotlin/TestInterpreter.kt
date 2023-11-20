import interpreter.Interpreter
import org.example.main
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows
import java.lang.IllegalArgumentException

public class TestInterpreter {
    private lateinit var interpreter: Interpreter


    @BeforeEach
    fun setInterpreter() {
        interpreter = Interpreter()

    }
    @Test
    fun testEmptyCode() : Unit {
        val code = """
           BEGIN
           END.
        """
        interpreter.eval(code)
        val res = interpreter.variables.toString()
        assertEquals("{}", res)
    }

    @Test
    fun testSimpleCode() : Unit {
        val code = """
            BEGIN
	            x := 2 + 3 * (2 + 3);
                y := 2 / 2 - 2 + 3 * ((1 + 1) + (1 + 1));
            END.
        """
        interpreter.eval(code)
        val res = interpreter.variables.toString()
        assertEquals("{x=17.0, y=11.0}", res)
    }

    @Test
    fun testInnerCode() : Unit {
        val code = """
            BEGIN
                y: = 2;
                BEGIN
                    a := 3;
                    a := a;
                    b := 10 + a + 10 * y / 4;
                    c := a - b
                END;
                x := 11;
            END.
        """
        interpreter.eval(code)
        val res = interpreter.variables.toString()
        assertEquals("{y=2.0, a=3.0, b=18.0, c=-15.0, x=11.0}", res)
    }

    @Test
    fun testUnarCode() : Unit {
        val code = """
            BEGIN
                y := -3;
                x := +24;
            END.
        """
        interpreter.eval(code)
        val res = interpreter.variables.toString()
        assertEquals("{y=-3.0, x=24.0}", res)
    }

    @Test
    fun testInvalidEnd() : Unit {
        val code = """
            BEGIN
	            x := 2 + 3 * (2 + 3);
                y := 2 / 2 - 2 + 3 * ((1 + 1) + (1 + 1));
        """
        assertThrows<IllegalArgumentException>{interpreter.eval(code)}
    }

    @Test
    fun testUnknownVar() : Unit {
        val code = """
            BEGIN
	            x := 2;
                y := a + x + 8;
            END.
        """
        assertThrows<IllegalArgumentException>{interpreter.eval(code)}
    }

    @Test
    fun testInvalidBinOperator() : Unit {
        val code = """
            BEGIN
                y := 2 \ 4;
            END.
        """
        assertThrows<IllegalArgumentException>{interpreter.eval(code)}
    }

    @Test
    fun testInvalidUnarOperator() : Unit {
        val code = """
            BEGIN
                y := *4;
            END.
        """
        assertThrows<IllegalArgumentException>{interpreter.eval(code)}
    }

    @Test
    fun testInvalidLexer() : Unit {
        val code = """
            BEGIN
                x - 3;
            END.
        """
        assertThrows<IllegalArgumentException>{interpreter.eval(code)}
    }

    @Test
    fun testInvalidVariable() : Unit {
        val code = """
            BEGIN
                x := -;
            END.
        """
        assertThrows<IllegalArgumentException>{interpreter.eval(code)}
    }

    @Test
    fun testInvalidAssign() : Unit {
        val code = """
            BEGIN
                x :^ 3;
            END.
        """
        assertThrows<IllegalArgumentException>{interpreter.eval(code)}
    }

    @Test
    fun testInvalidOrder() : Unit {
        val code = """
            BEGIN
                Ba := 2
            END.
        """
        assertThrows<IllegalArgumentException>{interpreter.eval(code)}
    }

    @Test
    fun testInvalidBegin() : Unit {
        val code = """
	            x := 2 + 3 * (2 + 3);
                y := 2 / 2 - 2 + 3 * ((1 + 1) + (1 + 1));
            END.
        """.trimIndent()
        assertThrows<IllegalArgumentException>{interpreter.eval(code)}
    }

    @Test
    fun testInvalidToken() : Unit {
        val code = """
             BEGIN
                x := 2 + 3; 
             END
        """.trimIndent()
        assertThrows<IllegalArgumentException>{interpreter.eval(code)}
    }


    @Test
    fun test_main() : Unit {
        assertEquals(0, main())
    }
}