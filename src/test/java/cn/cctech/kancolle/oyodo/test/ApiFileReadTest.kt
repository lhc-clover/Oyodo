package cn.cctech.kancolle.oyodo.test

import org.junit.Test
import java.io.FileWriter
import java.util.*

class ApiFileReadTest {

    @Test
    fun file() {
        FileWriter("apis/test").append(Date().toString()).flush()
        System.out.println("file")
    }
}
