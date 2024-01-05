package ir.farsroidx.core.model

import java.io.Serializable

data class SerializedData<T: Any>(val serialized: T): Serializable
