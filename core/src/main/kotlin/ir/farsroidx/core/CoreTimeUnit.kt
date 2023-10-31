@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package ir.farsroidx.core

sealed class CoreTimeUnit (val value: Int) {
    data object Seconds : CoreTimeUnit( 1           )
    data object Minutes : CoreTimeUnit( 60          )
    data object Hour    : CoreTimeUnit( 3_600       )
    data object Day     : CoreTimeUnit( 86_400      )
    data object Week    : CoreTimeUnit( 604_800     )
    data object Month   : CoreTimeUnit( 18_144_000  )
    data object Year    : CoreTimeUnit( 217_728_000 )
}