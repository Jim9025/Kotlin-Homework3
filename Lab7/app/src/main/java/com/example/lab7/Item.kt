data class Item(
    val photo: Int,
    val name: String,
    val price: Int,
    var quantity: Int = 1,         // 可變動
    var checked: Boolean = false   // ListView / RecyclerView 用
)
