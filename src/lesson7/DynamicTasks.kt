@file:Suppress("UNUSED_PARAMETER")

package lesson7

import java.lang.Integer.max
import java.lang.StringBuilder

/**
 * Наибольшая общая подпоследовательность.
 * Средняя
 *
 * Дано две строки, например "nematode knowledge" и "empty bottle".
 * Найти их самую длинную общую подпоследовательность -- в примере это "emt ole".
 * Подпоследовательность отличается от подстроки тем, что её символы не обязаны идти подряд
 * (но по-прежнему должны быть расположены в исходной строке в том же порядке).
 * Если общей подпоследовательности нет, вернуть пустую строку.
 * Если есть несколько самых длинных общих подпоследовательностей, вернуть любую из них.
 * При сравнении подстрок, регистр символов *имеет* значение.
 */

/*
    Быстродействие: O(mn)
    Ресурсоёмкость: O(mn)
*/

fun longestCommonSubSequence(first: String, second: String): String {
    val subSequence = StringBuilder()
    var m = first.length
    var n = second.length
    val table = Array(m + 1) { IntArray(n + 1) }

    for (i in table.indices) {
        for (j in table[i].indices) {
            when {
                i == 0 || j == 0 -> table[i][j] = 0
                first[i - 1] == second[j - 1] -> table[i][j] = table[i - 1][j - 1] + 1
                else -> table[i][j] = max(table[i - 1][j], table[i][j - 1])
            }
        }
    }

    var index = table[m][n]
    while (m > 0 && n > 0) {
        when {
            first[m - 1] == second[n - 1] -> {
                subSequence.append(first[m - 1])
                m--
                n--
                index--
            }
            table[m - 1][n] > table[m][n - 1] -> m--
            else -> n--
        }
    }

    return subSequence.toString().reversed()
}

/**
 * Наибольшая возрастающая подпоследовательность
 * Сложная
 *
 * Дан список целых чисел, например, [2 8 5 9 12 6].
 * Найти в нём самую длинную возрастающую подпоследовательность.
 * Элементы подпоследовательности не обязаны идти подряд,
 * но должны быть расположены в исходном списке в том же порядке.
 * Если самых длинных возрастающих подпоследовательностей несколько (как в примере),
 * то вернуть ту, в которой числа расположены раньше (приоритет имеют первые числа).
 * В примере ответами являются 2, 8, 9, 12 или 2, 5, 9, 12 -- выбираем первую из них.
 */

/*
    Быстродействие: O(n*log(n))
    Ресурсоёмкость: S(n)
*/

fun longestIncreasingSubSequence(list: List<Int>): List<Int> {
    val n = list.size

    if (n == 0) return listOf()
    if (n == 1) return listOf(list[0])

    val posInf = Integer.MAX_VALUE
    val negInf = Integer.MIN_VALUE
    val I = IntArray(n + 1)
    val L = IntArray(n)

    //Нахождение длины наибольшей возрастающей последовательности
    I[0] = negInf
    for (i in 1..n) {
        I[i] = posInf
    }

    var lisLength = 0

    for (i in 0 until n) {
        var low = 0
        var high = lisLength

        while (low <= high) {
            val mid = (low + high) / 2
            if (I[mid] < list[i]) low = mid + 1
            else high = mid - 1
        }
        I[low] = list[i]
        L[i] = low
        if (lisLength < low) lisLength = low
    }

    //Воссоздаём последовательность

    val lis = mutableListOf<Int>()
    var maxL = -1
    var maxLIndex = -1
    for (i in L.indices) {
        if (L[i] > maxL) {
            maxL = L[i]
            maxLIndex = i
        }
    }
    lis.add(list[maxLIndex])
    maxL--
    while (maxL != 0) {
        for (i in L.indices) {
            if (L[i] == maxL && list[i] < lis.last()) {
                lis.add(list[i])
                maxL--
                break
            }
        }
    }

    return lis.asReversed()
}

/**
 * Самый короткий маршрут на прямоугольном поле.
 * Средняя
 *
 * В файле с именем inputName задано прямоугольное поле:
 *
 * 0 2 3 2 4 1
 * 1 5 3 4 6 2
 * 2 6 2 5 1 3
 * 1 4 3 2 6 2
 * 4 2 3 1 5 0
 *
 * Можно совершать шаги длиной в одну клетку вправо, вниз или по диагонали вправо-вниз.
 * В каждой клетке записано некоторое натуральное число или нуль.
 * Необходимо попасть из верхней левой клетки в правую нижнюю.
 * Вес маршрута вычисляется как сумма чисел со всех посещенных клеток.
 * Необходимо найти маршрут с минимальным весом и вернуть этот минимальный вес.
 *
 * Здесь ответ 2 + 3 + 4 + 1 + 2 = 12
 */
fun shortestPathOnField(inputName: String): Int {
    TODO()
}

// Задачу "Максимальное независимое множество вершин в графе без циклов"
// смотрите в уроке 5