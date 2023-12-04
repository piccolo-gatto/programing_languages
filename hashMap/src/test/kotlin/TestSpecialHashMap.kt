import SpecialHashMap.SpecialHashMap
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows
import java.lang.IllegalArgumentException

class TestSpecialHashMap {
    private lateinit var map: SpecialHashMap<Int>


    @BeforeEach
    fun setSpecialHashMap() {
        map = SpecialHashMap()

    }

    @Test
    fun testIloc() {
        map["value1"] = 1
        map["value2"] = 2
        map["value3"] = 3
        map["1"] = 10
        map["2"] = 20
        map["3"] = 30
        map["1, 5"] = 100
        map["5, 5"] = 200
        map["10, 5"] = 300

        assertEquals(10, map.iloc[0])
        assertEquals(300, map.iloc[2])
        assertEquals(200, map.iloc[5])
        assertEquals(3, map.iloc[8])
    }

    @Test
    fun testPloc() {
        map["value1"] = 1
        map["value2"] = 2
        map["value3"] = 3
        map["1"] = 10
        map["2"] = 20
        map["3"] = 30
        map["(1, 5)"] = 100
        map["(5, 5)"] = 200
        map["(10, 5)"] = 300
        map["(1, 5, 3)"] = 400
        map["(5, 5, 4)"] = 500
        map["(10, 5, 5)"] = 600

        assertEquals(hashMapOf("1" to 10, "2" to 20, "3" to 30), map.ploc[">=1"])
        assertEquals(hashMapOf("1" to 10, "2" to 20), map.ploc["<3"])
        assertEquals(hashMapOf("1" to 10, "2" to 20), map.ploc["<>3"])
        assertEquals(hashMapOf("3" to 30), map.ploc["=3"])
        assertEquals(hashMapOf("(1, 5)" to 100, "(5, 5)" to 200, "(10, 5)" to 300), map.ploc[">0, >0"])
        assertEquals(hashMapOf("(10, 5)" to 300), map.ploc[">=10, >0"])
        assertEquals(hashMapOf("(1, 5, 3)" to 400), map.ploc["<5, >=5, >=3"])
        assertEquals(hashMapOf("(1, 5, 3)" to 400), map.ploc["<5A >=5A >=3"])
        assertEquals(hashMapOf("(1, 5, 3)" to 400), map.ploc["<5f >=5f >=3"])
        assertEquals(hashMapOf("(1, 5, 3)" to 400), map.ploc["<5: >=5: >=3"])
        assertEquals(hashMapOf("(1, 5, 3)" to 400), map.ploc["<5; >=5; >=3"])
        assertEquals(hashMapOf("(1, 5, 3)" to 400), map.ploc["<5_ >=5_ >=3"])
    }

    @Test
    fun testPlocException() {
        map["value1"] = 1
        map["value2"] = 2
        map["value3"] = 3
        map["1"] = 10
        map["2"] = 20
        map["3"] = 30
        map["(1, 5)"] = 100
        map["(5, 5)"] = 200
        map["(10, 5)"] = 300
        map["(1, 5, 3)"] = 400
        map["(5, 5, 4)"] = 500
        map["(10, 5, 5)"] = 600

        assertThrows<IllegalArgumentException> { map.ploc["*3"] }
        assertThrows<IllegalArgumentException> { map.ploc["===3"] }
    }
}