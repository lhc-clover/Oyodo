package cn.cctech.kancolle.oyodo

interface Watcher<in T> {

    fun onChange(data: T)

}
