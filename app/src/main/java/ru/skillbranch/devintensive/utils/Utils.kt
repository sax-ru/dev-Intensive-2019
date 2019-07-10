package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullName : String?) : Pair<String?, String?> {
        val parts : List<String>? = fullName?.split(" ")
        val firstName = parts?.getOrNull(0)?.trim()?.ifEmpty { null }
        val lastName = parts?.getOrNull(1)?.trim()?.ifEmpty { null }
        return firstName to lastName
    }

    fun toInitials(firstName : String?, lastName : String?) : String? {
        val first = firstName?.trim()?.ifEmpty { null }?.firstOrNull()?.toUpperCase()
        val second = lastName?.trim()?.ifEmpty { null }?.firstOrNull()?.toUpperCase()
        return when {
            first == null && second == null -> null
            first != null && second == null -> first.toString()
            first == null && second != null -> second.toString()
            first != null && second != null -> first.toString() + second.toString()
            else -> null
        }
    }

    fun transliteration(payload : String, divider : String = " ") : String {
        var result = ""
        val parts : List<String> = payload.split(" ")
        var isFirstTime = true
        for (part in parts) {
            if (!isFirstTime) {
                result += divider
            }
            val charArray = part.toCharArray()
            for (char in charArray) {
                result += letterTransliteration(char.toString())
            }
            isFirstTime = false
        }
        return result
    }

    private fun letterTransliteration(letter : String) : String {
        return when (letter) {
            "А" -> "A"
            "Б" -> "B"
            "В" -> "V"
            "Г" -> "G"
            "Д" -> "D"
            "Е" -> "E"
            "Ё" -> "E"
            "Ж" -> "Zh"
            "З" -> "Z"
            "И" -> "I"
            "Й" -> "I"
            "К" -> "K"
            "Л" -> "L"
            "М" -> "M"
            "Н" -> "N"
            "О" -> "O"
            "П" -> "P"
            "Р" -> "R"
            "С" -> "S"
            "Т" -> "T"
            "У" -> "U"
            "Ф" -> "F"
            "Х" -> "H"
            "Ц" -> "C"
            "Ч" -> "Ch"
            "Ш" -> "Sh"
            "Щ" -> "Sh'"
            "Ъ" -> ""
            "Ы" -> "I"
            "Ь" -> ""
            "Э" -> "E"
            "Ю" -> "Yu"
            "Я" -> "Ya"
            "а" -> "a"
            "б" -> "b"
            "в" -> "v"
            "г" -> "g"
            "д" -> "d"
            "е" -> "e"
            "ё" -> "e"
            "ж" -> "zh"
            "з" -> "z"
            "и" -> "i"
            "й" -> "i"
            "к" -> "k"
            "л" -> "l"
            "м" -> "m"
            "н" -> "n"
            "о" -> "o"
            "п" -> "p"
            "р" -> "r"
            "с" -> "s"
            "т" -> "t"
            "у" -> "u"
            "ф" -> "f"
            "х" -> "h"
            "ц" -> "c"
            "ч" -> "ch"
            "ш" -> "sh"
            "щ" -> "sh'"
            "ъ" -> ""
            "ы" -> "i"
            "ь" -> ""
            "э" -> "e"
            "ю" -> "yu"
            "я" -> "ya"
            else -> letter
        }
    }
}