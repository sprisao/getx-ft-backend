package kr.getx.fitnessteachers.utils

object StringConversionUtils {
    fun convertListToString(list: List<String>): String {
        return list.joinToString(",")
    }

    fun convertStringToList(string: String): List<String> {
        return string.split(",")
    }
}