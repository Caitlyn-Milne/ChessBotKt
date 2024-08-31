package cutelyn

infix fun UShort.shl(int : Int) : UShort {
    return (this.toInt() shl int).toUShort()
}

infix fun UByte.shl(int : Int) : UByte {
    return (this.toInt() shl int).toUByte()
}

infix fun UShort.shr(int : Int) : UShort {
    return (this.toInt() shr int).toUShort()
}

infix fun UByte.shr(int : Int) : UByte {
    return (this.toInt() shr int).toUByte()
}