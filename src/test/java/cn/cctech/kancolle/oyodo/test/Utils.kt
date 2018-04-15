package cn.cctech.kancolle.oyodo.test

import cn.cctech.kancolle.oyodo.apis.JsonBean
import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.StringReader
import java.lang.reflect.Type

private const val apiDir = "apis"

fun <T : JsonBean> readApiFile(name: String, token: Type): T {
    val jsonReader = FileReader(apiDir + File.separator + name)
    jsonReader.skip(7)
    return Gson().fromJson<T>(jsonReader, token)
}

fun <T : JsonBean> readApiFileWithParams(name: String, token: Type): T {
    val jsonReader = FileReader(apiDir + File.separator + name)
    val lineReader = BufferedReader(jsonReader)
    val params = lineReader.readLine()
    lineReader.skip(7)
    val reader = JsonReader(lineReader)
    reader.isLenient = true
    val bean = Gson().fromJson<T>(reader, token)
    bean.setParams(params)
    return bean
}