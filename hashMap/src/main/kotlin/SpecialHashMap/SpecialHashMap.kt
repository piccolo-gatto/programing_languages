package SpecialHashMap

open class SpecialHashMap<ValueType> : HashMap<String, ValueType>() {
    val iloc = Iloc(this)
    val ploc = Ploc(this)
}