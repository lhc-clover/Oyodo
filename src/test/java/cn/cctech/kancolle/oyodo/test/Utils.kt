package cn.cctech.kancolle.oyodo.test

import com.google.gson.Gson
import java.io.File
import java.io.FileReader
import java.lang.reflect.Type

private const val apiDir = "apis"

fun <T> readApiFile(name: String, token: Type): T {
    val jsonReader = FileReader(apiDir + File.separator + name)
    jsonReader.skip(7)
    return Gson().fromJson<T>(jsonReader, token)
}