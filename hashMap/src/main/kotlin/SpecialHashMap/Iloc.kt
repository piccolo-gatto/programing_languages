package SpecialHashMap

class Iloc<ValueType>(private val specialHashMap: SpecialHashMap<ValueType>) {
    operator fun get(index: Int): ValueType? {
        val sort = specialHashMap.keys.toSortedSet().toList()
        return specialHashMap.get(sort.get(index))
    }
}

