@file:Suppress("UNUSED_PARAMETER", "unused")
package lesson7.task1

/**
 * Ячейка матрицы: row = ряд, column = колонка
 */
data class Cell(val row: Int, val column: Int)

/**
 * Интерфейс, описывающий возможности матрицы. E = тип элемента матрицы
 */
interface Matrix<E> {
    /** Высота */
    val height: Int

    /** Ширина */
    val width: Int

    /**
     * Доступ к ячейке.
     * Методы могут бросить исключение, если ячейка не существует или пуста
     */
    operator fun get(row: Int, column: Int): E
    operator fun get(cell: Cell): E

    /**
     * Запись в ячейку.
     * Методы могут бросить исключение, если ячейка не существует
     */
    operator fun set(row: Int, column: Int, value: E)
    operator fun set(cell: Cell, value: E)
}

/**
 * Простая
 *
 * Метод для создания матрицы, должен вернуть РЕАЛИЗАЦИЮ Matrix<E>.
 * height = высота, width = ширина, e = чем заполнить элементы.
 * Бросить исключение IllegalArgumentException, если height или width <= 0.
 */
fun <E> createMatrix(height: Int, width: Int, e: E): Matrix<E> {
    val myMatrix = MatrixImpl<E>(height, width, e)
    for (row in 0 until height) {
        for (column in 0 until width) {
            myMatrix[row, column] = e
        }
    }
    if (height <= 0 || width <= 0)
        throw IllegalArgumentException("матрицы не существует")
    return myMatrix
}

/**
 * Средняя сложность
 *
 * Реализация интерфейса "матрица"
 */
class MatrixImpl<E>(override val height: Int, override val width: Int, e: E) : Matrix<E> {
    private val listOfCell = mutableListOf<E>()

    init {
        for (i in 0 until height * width) {
            listOfCell.add(e)
        }
    }

    override fun get(row: Int, column: Int): E = listOfCell[row * width + column]

    override fun get(cell: Cell): E = listOfCell[width * cell.row + cell.column]

    override fun set(row: Int, column: Int, value: E) {
        listOfCell[row * width + column] = value
    }

    override fun set(cell: Cell, value: E) {
        listOfCell[cell.row * width + cell.column] = value
    }

    override fun equals(other: Any?): Boolean {
        if (other?.javaClass is Matrix<*>) return true
        if (this === other) return true
        other as MatrixImpl<*>
        if (height != other.height) return false
        if (width != other.width) return false
        if (listOfCell != other.listOfCell) return false
        return true
    }

    override fun toString(): String {
        val res = StringBuilder()
        res.append("[")
        for (i in 0 until height) {
            res.append("[")
            for (j in 0 until width) {
                res.append(this[i, j])
                if (j != height - 1)
                    res.append(" ")
            }
            res.append("]")
        }
        res.append("]")
        return res.toString()
    }
}
