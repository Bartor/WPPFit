package various.coders.wppfit.support

class SynchronizedCaller(size: Int, private val callback: () -> Unit) {
    private val called = BooleanArray(size)

    public fun call(index: Int) {
        called[index] = true
        if (called.all { it }) callback()
    }
}