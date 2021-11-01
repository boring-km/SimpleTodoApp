package com.boringkm.simpletodo.domain

data class TodoItem (
    val data: String,
    var checked: Boolean
) {
    override fun equals(other: Any?): Boolean {
        if (other is TodoItem) {    // null도 아니고 TodoItem 일 때
            if (this.data == other.data)
                if (this.checked == other.checked)
                    return true
        }
        return false
    }

    override fun hashCode(): Int {
        var result = data.hashCode()
        result = 31 * result + checked.hashCode()
        return result
    }
}
