@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package ir.farsroidx.core

sealed class CoreTimeUnit (val value: Int) {
    object Seconds : CoreTimeUnit( 1           )
    object Minutes : CoreTimeUnit( 60          )
    object Hour    : CoreTimeUnit( 3_600       )
    object Day     : CoreTimeUnit( 86_400      )
    object Week    : CoreTimeUnit( 604_800     )
    object Month   : CoreTimeUnit( 18_144_000  )
    object Year    : CoreTimeUnit( 217_728_000 )
}