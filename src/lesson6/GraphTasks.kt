@file:Suppress("UNUSED_PARAMETER", "unused")

package lesson6

import java.lang.IllegalStateException

/**
 * Эйлеров цикл.
 * Средняя
 *
 * Дан граф (получатель). Найти по нему любой Эйлеров цикл.
 * Если в графе нет Эйлеровых циклов, вернуть пустой список.
 * Соседние дуги в списке-результате должны быть инцидентны друг другу,
 * а первая дуга в списке инцидентна последней.
 * Длина списка, если он не пуст, должна быть равна количеству дуг в графе.
 * Веса дуг никак не учитываются.
 *
 * Пример:
 *
 *      G -- H
 *      |    |
 * A -- B -- C -- D
 * |    |    |    |
 * E    F -- I    |
 * |              |
 * J ------------ K
 *
 * Вариант ответа: A, E, J, K, D, C, H, G, B, C, I, F, B, A
 *
 * Справка: Эйлеров цикл -- это цикл, проходящий через все рёбра
 * связного графа ровно по одному разу
 */
fun Graph.findEulerLoop(): List<Graph.Edge> {
    TODO()
}

/**
 * Минимальное остовное дерево.
 * Средняя
 *
 * Дан связный граф (получатель). Найти по нему минимальное остовное дерево.
 * Если есть несколько минимальных остовных деревьев с одинаковым числом дуг,
 * вернуть любое из них. Веса дуг не учитывать.
 *
 * Пример:
 *
 *      G -- H
 *      |    |
 * A -- B -- C -- D
 * |    |    |    |
 * E    F -- I    |
 * |              |
 * J ------------ K
 *
 * Ответ:
 *
 *      G    H
 *      |    |
 * A -- B -- C -- D
 * |    |    |
 * E    F    I
 * |
 * J ------------ K
 */
fun Graph.minimumSpanningTree(): Graph {
    TODO()
}

/**
 * Максимальное независимое множество вершин в графе без циклов.
 * Сложная
 *
 * Дан граф без циклов (получатель), например
 *
 *      G -- H -- J
 *      |
 * A -- B -- D
 * |         |
 * C -- F    I
 * |
 * E
 *
 * Найти в нём самое большое независимое множество вершин и вернуть его.
 * Никакая пара вершин в независимом множестве не должна быть связана ребром.
 *
 * Если самых больших множеств несколько, приоритет имеет то из них,
 * в котором вершины расположены раньше во множестве this.vertices (начиная с первых).
 *
 * В данном случае ответ (A, E, F, D, G, J)
 *
 * Если на входе граф с циклами, бросить IllegalArgumentException
 *
 * Эта задача может быть зачтена за пятый и шестой урок одновременно
 */

/*  Быстродействие: O(V + E)
    Ресурсоёмкость: S(V)
 */

fun Graph.cycleSearch(): Boolean {
    val vertices = mutableMapOf<Graph.Vertex, Boolean>() //Храним пары вершина + 'удалена' она или нет
    this.vertices.forEach { vertices[it] = false } //Изначально все вершины присутствуют
    if (vertices.size <= 2) return false //Очевидно, в графе с двумя и менее вершинами цикла быть не может
    /*
    'Удаляем' вершины, которые или изолированы, или имеют одного соседа до тех пор, пока:
    а) не останется ни одной вершины (все значения в vertices = true, циклов нет - возвращаем false)
    б) останутся вершины с двумя и более соседями (если есть хоть одно значение false, циклы есть - возвращаем true)
     */
    fun delete(v: Graph.Vertex) {
        val neighbours = this.getNeighbors(v).filter { vertices[it] == false }
        if (neighbours.size <= 1) {
            vertices[v] = true
            neighbours.forEach { delete(it) }
        }
    }
    this.vertices.forEach { if (vertices[it] == false) delete(it) }
    //Проверяем, что все вершины 'удалены'
    vertices.values.forEach { if (!it) return true }
    return false
}

/*  Быстродействие: O(V + E)
    Ресурсоёмкость: S(V)
 */

fun Graph.largestIndependentVertexSet(): Set<Graph.Vertex> {
    if (cycleSearch()) throw IllegalStateException() //Требуем, чтобы граф был ацикличным

    val res: MutableSet<Graph.Vertex>
    val possibleSet = mutableSetOf<Graph.Vertex>()
    val possibleSet2 = mutableSetOf<Graph.Vertex>()
    val visitedVertices = mutableListOf<Graph.Vertex>()

    /* PossibleSet - сет, включающий стартовую вершину (с которой начинается обход графа, пусть А)
    Поскольку может возникнуть ситуация, когда наибольшее множество не содержит начальную вершину
    (см. AbstractGraphTests.kt -> граф max), то создаём ещё один сет - PossibleSet2,
    который начинается с первого взятого соседа А.
    Чтобы выяснить, какое является наибольшим, впоследствии сравним их размер и запишем максимальное по количеству
    вершин множество в конечный результат - res. */

    for (v in this.vertices) {
        if (v !in visitedVertices) {
            visitedVertices.add(v)
            val neighbours = this.getNeighbors(v)   //непосредственные соседи
            val temp = mutableSetOf<Graph.Vertex>()
            neighbours.forEach { it ->
                temp.addAll(
                    this.getNeighbors(it).filter { it != v })
            }   //соседи соседей, исключая начальную точку
            /*  Если точка изолирована или ни один из её соседей ещё не включен в результат, записываем в possibleSet.
                Добавляем в possibleSet2 вершину, если вдруг множество, в которое входит хотя бы один из её соседей,
                не является наибольшим  */
            if (neighbours.size == 0 || !(neighbours.any { possibleSet.contains(it) })) possibleSet.add(v)
            if ((neighbours.any { possibleSet.contains(it) })) possibleSet2.add(v)
            /*  Если сосед соседа ещё не был посещён и он не является соседом уже включённой в possibleSet вершины,
             записываем в possibleSet   */
            for (n in temp) {
                if (n !in visitedVertices && !(neighbours.any { possibleSet.contains(it) })) possibleSet.add(n)
            }
        }
    }
    //Выясняем, какое множество больше (если равны, то берём множество, в которое входит первая вершина из обхода графа
    res = if (possibleSet.size >= possibleSet2.size) possibleSet
    else possibleSet2
    return res
}

/**
 * Наидлиннейший простой путь.
 * Сложная
 *
 * Дан граф (получатель). Найти в нём простой путь, включающий максимальное количество рёбер.
 * Простым считается путь, вершины в котором не повторяются.
 * Если таких путей несколько, вернуть любой из них.
 *
 * Пример:
 *
 *      G -- H
 *      |    |
 * A -- B -- C -- D
 * |    |    |    |
 * E    F -- I    |
 * |              |
 * J ------------ K
 *
 * Ответ: A, E, J, K, D, C, H, G, B, F, I
 */

/*  Быстродействие: O(V + E)
    Ресурсоёмкость: S(V)
 */

fun Graph.longestSimplePath(): Path {
    /*
    Будем хранить возможные максимально длинные пути в allPaths. Изначально добавляем в него пути, в которых всего
    по одной вершине. Затем берём каждый такой путь и проверяем: если его длина больше текущей максимальной длины, то
    обновляем путь в longestSimplePath. Далее смотрим, можно ли продолжить данный путь. Если можно, то в allPaths
    записываем эти новые возможные пути, а текущий удаляем, так как он уже, в таком случае, точно не максимальный.
     */

    var longestSimplePath = Path()
    val allPaths = mutableSetOf<Path>()
    this.vertices.forEach { allPaths.add(Path(it)) }

    while (allPaths.size != 0) {
        val currentPath = allPaths.last()
        if (longestSimplePath.length < currentPath.length) longestSimplePath = currentPath
        val neighbours = this.getNeighbors(currentPath.vertices.last())
        for (vertex in neighbours) {
            if (currentPath.contains(vertex)) continue
            else allPaths.add(Path(currentPath, this, vertex))
        }
        allPaths.remove(currentPath)
    }

    return longestSimplePath
}

/**
 * Балда
 * Сложная
 *
 * Задача хоть и не использует граф напрямую, но решение базируется на тех же алгоритмах -
 * поэтому задача присутствует в этом разделе
 *
 * В файле с именем inputName задана матрица из букв в следующем формате
 * (отдельные буквы в ряду разделены пробелами):
 *
 * И Т Ы Н
 * К Р А Н
 * А К В А
 *
 * В аргументе words содержится множество слов для поиска, например,
 * ТРАВА, КРАН, АКВА, НАРТЫ, РАК.
 *
 * Попытаться найти каждое из слов в матрице букв, используя правила игры БАЛДА,
 * и вернуть множество найденных слов. В данном случае:
 * ТРАВА, КРАН, АКВА, НАРТЫ
 *
 * И т Ы Н     И т ы Н
 * К р а Н     К р а н
 * А К в а     А К В А
 *
 * Все слова и буквы -- русские или английские, прописные.
 * В файле буквы разделены пробелами, строки -- переносами строк.
 * Остальные символы ни в файле, ни в словах не допускаются.
 */
fun baldaSearcher(inputName: String, words: Set<String>): Set<String> {
    TODO()
}