package lesson7

import kotlin.test.assertEquals

abstract class AbstractDynamicTests {
    fun longestCommonSubSequence(longestCommonSubSequence: (String, String) -> String) {
        assertEquals("", longestCommonSubSequence("", ""))
        assertEquals("", longestCommonSubSequence("мой мир", "я"))
        assertEquals("1", longestCommonSubSequence("1", "1"))
        assertEquals("13", longestCommonSubSequence("123", "13"))
        assertEquals("здс", longestCommonSubSequence("здравствуй мир", "мы здесь"))
        assertEquals("emt ole", longestCommonSubSequence("nematode knowledge", "empty bottle"))
        val expectedLength = "e kerwelkkd r".length
        assertEquals(
            expectedLength, longestCommonSubSequence(
                "oiweijgw kejrhwejelkrw kjhdkfjs hrk",
                "perhkhk lerkerorwetp lkjklvvd durltr"
            ).length, "Answer must have length of $expectedLength, e.g. 'e kerwelkkd r' or 'erhlkrw kjk r'"
        )
        val expectedLength2 = """ дд саы чтых,
евшнео ваа се сви дн.
        """.trimIndent().length
        assertEquals(
            expectedLength2, longestCommonSubSequence(
                """
Мой дядя самых честных правил,
Когда не в шутку занемог,
Он уважать себя заставил
И лучше выдумать не мог.
                """.trimIndent(),
                """
Так думал молодой повеса,
Летя в пыли на почтовых,
Всевышней волею Зевеса
Наследник всех своих родных.
                """.trimIndent()
            ).length, "Answer must have length of $expectedLength2"
        )

        val mayakovsky = """
Все чаще думаю -
не поставить ли лучше
точку пули в своем конце.
Сегодня я
на всякий случай
даю прощальный концерт.
    """.trimIndent()
        assertEquals(
            mayakovsky, longestCommonSubSequence(
                """
За всех вас,
которые нравились или нравятся,
хранимых иконами у души в пещере,
как чашу вина в застольной здравице,
подъемлю стихами наполненный череп.
Все чаще думаю -
не поставить ли лучше
точку пули в своем конце.
Сегодня я
на всякий случай
даю прощальный концерт.
            """.trimIndent(),
                """
Все чаще думаю -
не поставить ли лучше
точку пули в своем конце.
Сегодня я
на всякий случай
даю прощальный концерт.
Память!
Собери у мозга в зале
любимых неисчерпаемые очереди.
Смех из глаз в глаза лей.
Былыми свадьбами ночь ряди.
Из тела в тело веселье лейте.
Пусть не забудется ночь никем.
Я сегодня буду играть на флейте.
На собственном позвоночнике.
            """.trimIndent()
            )
        )
    }

    fun longestIncreasingSubSequence(longestIncreasingSubSequence: (List<Int>) -> List<Int>) {
        assertEquals(listOf(), longestIncreasingSubSequence(listOf()))
        assertEquals(listOf(1), longestIncreasingSubSequence(listOf(1)))
        assertEquals(listOf(1, 2), longestIncreasingSubSequence(listOf(1, 2)))
        assertEquals(listOf(2), longestIncreasingSubSequence(listOf(2, 1)))
        assertEquals(
            listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10),
            longestIncreasingSubSequence(listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))
        )
        assertEquals(listOf(2, 8, 9, 12), longestIncreasingSubSequence(listOf(2, 8, 5, 9, 12, 6)))
        assertEquals(
            listOf(23, 34, 56, 87, 91, 98, 140, 349), longestIncreasingSubSequence(
                listOf(
                    23, 76, 34, 93, 123, 21, 56, 87, 91, 12, 45, 98, 140, 12, 5, 38, 349, 65, 94,
                    45, 76, 15, 99, 100, 88, 84, 35, 88
                )
            )
        )
        assertEquals(listOf(1), longestIncreasingSubSequence(listOf(1, 1, 1, 1, 1, 1)))
        assertEquals(listOf(100), longestIncreasingSubSequence(listOf(100, 99, 98, 97, 96, 95)))
    }

    fun shortestPathOnField(shortestPathOnField: (String) -> Int) {
        assertEquals(1, shortestPathOnField("input/field_in2.txt"))
        assertEquals(12, shortestPathOnField("input/field_in1.txt"))
        assertEquals(43, shortestPathOnField("input/field_in3.txt"))
        assertEquals(28, shortestPathOnField("input/field_in4.txt"))
        assertEquals(222, shortestPathOnField("input/field_in5.txt"))
        assertEquals(15, shortestPathOnField("input/field_in6.txt"))
    }

}