import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows
import java.lang.IllegalArgumentException

class MainTest {

    @Test
    fun test_check_operator_true_result() {
        assertTrue(check_operator("+"))
        assertTrue(check_operator("-"))
        assertTrue(check_operator("*"))
        assertTrue(check_operator("/"))
    }

    @Test
    fun test_check_operator_false_result() {
        assertFalse(check_operator("%"))
        assertFalse(check_operator("5"))
        assertFalse(check_operator("aaa"))
        assertFalse(check_operator(""))
        assertFalse(check_operator(" "))
    }

    @Test
    fun test_check_operand_true_result() {
        assertTrue(check_operand("2"))
        assertTrue(check_operand("33"))
    }

    @Test
    fun test_check_operatnd_false_result() {
        assertFalse(check_operand("-1"))
        assertFalse(check_operand("ftg"))
        assertFalse(check_operand(""))
        assertFalse(check_operand(" "))
        assertFalse(check_operand("+"))
    }

    @Test
    fun test_to_infix() {
        assertEquals("((13 - 4) + 55)", to_infix("+ - 13 4 55"))
        assertEquals("(2 + (2 * (2 - 1)))", to_infix("+ 2 * 2 - 2 1"))
        assertEquals("((10 + 20) + 30)", to_infix("+ + 10 20 30"))
        assertEquals("((3 + 10) / ((2 + 3) * (3 - 5)))", to_infix("/ + 3 10 * + 2 3 - 3 5"))

    }

    @Test
    fun test_to_infix_throws() {
        assertThrows<IllegalArgumentException>{to_infix("- - 1 2")}
        assertThrows<IllegalArgumentException>{to_infix("- - 1 2")}
        assertThrows<IllegalArgumentException>{to_infix("")}
        assertThrows<IllegalArgumentException>{to_infix(" ")}
        assertThrows<IllegalArgumentException>{to_infix("+ + 10 o 5")}
        assertThrows<IllegalArgumentException>{to_infix("+ + -1 -16 45")}
        assertThrows<IllegalArgumentException>{to_infix("+ % 10 2")}
        assertThrows<IllegalArgumentException>{to_infix("1 2")}
        assertThrows<IllegalArgumentException>{to_infix("*")}
    }

    @Test
    fun test_main() {
        assertEquals(0, main())
    }
}