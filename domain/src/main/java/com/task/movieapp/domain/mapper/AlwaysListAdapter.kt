package com.task.movieapp.domain.mapper

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import com.google.gson.stream.MalformedJsonException
import java.io.IOException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

internal class AlwaysListTypeAdapterFactory() :
    TypeAdapterFactory // Gson can instantiate it itself {
{
    override fun <T> create(gson: Gson, typeToken: TypeToken<T>): TypeAdapter<T>? {
        // If it's not a List -- just delegate the job to Gson and let it pick the best type adapter itself
        if (!MutableList::class.java.isAssignableFrom(typeToken.rawType)) {
            return null
        }
        // Resolving the list parameter type
        val elementType: Type =
            resolveTypeArgument(typeToken.type)
        val elementTypeAdapter =
            gson.getAdapter(TypeToken.get(elementType)) as TypeAdapter
        // Note that the always-list type adapter is made null-safe, so we don't have to check nulls ourselves
        return AlwaysListTypeAdapter(elementTypeAdapter)
            .nullSafe() as TypeAdapter<T>
    }

    private class AlwaysListTypeAdapter<E>(private val elementTypeAdapter: TypeAdapter<E>) :
        TypeAdapter<List<E>?>() {
        override fun write(out: JsonWriter?, list: List<E>?) {
            throw UnsupportedOperationException()
        }

        @Throws(IOException::class)
        override fun read(`in`: JsonReader): List<E> {
            // This is where we detect the list "type"
            val list: MutableList<E> = ArrayList()
            when (val token: JsonToken = `in`.peek()) {
                JsonToken.BEGIN_ARRAY -> {
                    // If it's a regular list, just consume [, <all elements>, and ]
                    `in`.beginArray()
                    while (`in`.hasNext()) {
                        list.add(elementTypeAdapter.read(`in`))
                    }
                    `in`.endArray()
                }
                JsonToken.BEGIN_OBJECT, JsonToken.STRING, JsonToken.NUMBER, JsonToken.BOOLEAN ->                 // An object or a primitive? Just add the current value to the result list
                    list.add(elementTypeAdapter.read(`in`))
                else -> throw MalformedJsonException("Unexpected token: $token")
            }
            return list
        }

    }

    companion object {
        private fun resolveTypeArgument(type: Type): Type {
            // The given type is not parameterized?
            if (type !is ParameterizedType) {
                // No, raw
                return Any::class.java
            }
            val parameterizedType: ParameterizedType = type as ParameterizedType
            return parameterizedType.actualTypeArguments[0]
        }
    }
}

