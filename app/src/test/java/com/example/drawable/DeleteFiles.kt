package com.example.drawable

import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.attribute.BasicFileAttributes
import java.text.SimpleDateFormat
import java.util.*

/**
 *   yy
 *   2022/11/1
 */
class DeleteFiles {

//    private val path = "/Users/yy/123"
    private val path = "/Volumes/yyly/download/Mov/BT"

    private val noNeedDispose = 1
    private val needDelete = 2
    private val deleteFailed = 3
    private val omissive = 4

    fun deleteFiles ()
    {
        println("==================")
        var l = getFiles(path)
        println("==================")
        var needDeleteList = mutableListOf<String>()
        var deleteFailedList = mutableListOf<String>()
        var omissiveList = mutableListOf<String>()
        l?.forEach {
            var state = disposeFile(it)
            when (state)
            {
                needDelete->{needDeleteList.add(it)}
                deleteFailed->{deleteFailedList.add(it)}
//                omissive->{omissiveList.add(it)}
            }
        }

        println("========needDelete==========")
        needDeleteList.forEach { println(it) }
        println("=========deleteFailed=========")
        deleteFailedList.forEach { println(it) }
//        println("=========omissive=========")
//        omissiveList.forEach { println(it) }
        println("==================")

    }


    private fun getFiles (path:String):MutableList<String>
    {
        val list: MutableList<String> = ArrayList()
        val fileDir = File(path)
        // 判断是否是文件夹
        if (fileDir.isDirectory) {
            val fileList: Array<File> = fileDir.listFiles()
            for (i in fileList.indices) {
                if (fileList[i].isDirectory) {
                    // 使用递归获取文件夹下的文件夹中的文件
                    list.addAll(getFiles(fileList[i].path))
                } else {
//                    System.err.println(fileList[i].path)
                    list.add(fileList[i].absolutePath)
                }
            }
        }
        return list
    }

    private fun disposeFile (path:String):Int
    {
        val file = File(path)

        file?.let {
            if ( it.name.startsWith(".")) return noNeedDispose
            // 根据文件的绝对路径获取Path
            val path: Path = Paths.get(it.absolutePath)
            val attrs: BasicFileAttributes =
                Files.readAttributes(path, BasicFileAttributes::class.java)
            // 从基本属性类中获取文件创建时间
            val fileTime = attrs.creationTime()
            // 将文件创建时间转成毫秒
            val millis = fileTime.toMillis()
            val dateFormat = SimpleDateFormat("YYYY-MM-dd HH:mm:ss")
            val date = Date()
            date.time = millis
            // 毫秒转成时间字符串
            val time: String = dateFormat.format(date)

            var milliss = (millis - System.currentTimeMillis()) / 1000
            var oneDayTime = 86400
            var leaveDay = milliss / oneDayTime

            if (leaveDay == 0L )
            {
                if ( it.name.endsWith(".html") || it.name.endsWith(".txt") || it.name.contains("社 區 最 新") || it.name.contains("x u u"))
                {
                    if ( file.delete() )
                    {
                        println("delete : " + it.name + " : " + time + "距离今天" + leaveDay + "天")
                        return needDelete
                    }
                    return deleteFailed
                }
            }
        }
        return omissive
    }

}