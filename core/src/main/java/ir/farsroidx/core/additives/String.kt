@file:Suppress("unused")

package ir.farsroidx.core.additives

// TODO: String ========================================================================= String ===

fun String?.similarityWith(other: String?, digits: Int = 3): Double {

    require(
        !(this == null || other == null)
    ) {
        "Strings should not be null"
    }

    val maxLength = kotlin.math.max(this.length, other.length)

    val result = if (maxLength > 0) {
        (maxLength * 1.0 - this.getLevenshteinDistance(other)) / maxLength * 1.0
    } else 1.0

    return String.format("%.${digits}f", result * 100).toDouble()
}

private fun String.getLevenshteinDistance(other: String): Int {

    val m = this.length
    val n = other.length

    val t = Array(m + 1) { IntArray(n + 1) }

    for (i in 1..m) {
        t[i][0] = i
    }

    for (j in 1..n) {
        t[0][j] = j
    }

    var cost: Int

    for (i in 1..m) {

        for (j in 1..n) {

            cost = if (this[i - 1] == other[j - 1]) 0 else 1

            t[i][j] = kotlin.math.min(
                kotlin.math.min(t[i - 1][j] + 1, t[i][j - 1] + 1), t[i - 1][j - 1] + cost
            )
        }
    }

    return t[m][n]
}