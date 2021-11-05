package com.devbea.myusic.playlist

/**	1|0|0|0|0|0|0|0|0|0
a|b|c|d|e|f|g|h|i|j

repeating sequence
|fun getOnes(j): 	   from 1 to 12 use mapOfUniqueNumbers
|fun getTeens(ij):	   from 13 to 19 use mapOfTensPrefix + teen
|fun getTens(i):        from 20 to 99 use mapOfTensPrefix +ty +getOnes(x)


fun getHundreds(h):    from 100 to 900 use getOnes(h) + hundred(s)
fun getThousands(g):    from 1000 to 9000 use getOnes(g) + Thousand(s)
fun getMillions(d):    from 1000000 to 9000000 use getOnes(d) + Million(s)

input: 1234567

val millions= input.div(10000000)
 */

import java.util.*


enum class Numbers(val asText: String) {
    MILLION("Million"),
    THOUSAND("Thousand"),
    HUNDRED("Hundred"),
    TENS("Tens"),
    ONES(""),
    NOTSUPORTED("NOT SUPPORTED YET")
}

fun Int.length(): Int = this.toString().length

fun Int.asSuffix(suffix: String): String = if (this == 1) suffix else suffix + "s"


fun Int.asNumbers(): Numbers {
    return when (this.length()) {
        7, 8, 9 -> Numbers.MILLION
        4, 5, 6 -> Numbers.THOUSAND
        3 -> Numbers.HUNDRED
        2 -> Numbers.TENS
        1 -> Numbers.ONES
        else -> Numbers.NOTSUPORTED
    }
}

class NumberMapper : Function1<Int, String> {
    override fun invoke(input: Int): String {
        return if (mapOfUniqueNumbers.contains(input)) mapOfUniqueNumbers.get(input).toString()
        else when (input.asNumbers()) {
            Numbers.MILLION -> {
                getMillions(input.toString())
            }
            Numbers.THOUSAND -> {
                getThousands(input.toString())
            }
            Numbers.HUNDRED -> {
                getHundreds(input.toString())
            }
            Numbers.TENS -> {
                getTens(input.toString())
            }
            else -> ""
        }
    }


    private fun getTens(input: String): String {
        return if (input.toInt() < 20) {
            mapOfTens.getValue(input[1].digitToInt()) + "teen"
        } else {
            mapOfTens[input[0].digitToInt()] + "ty " + mapOfUniqueNumbers[input[1].digitToInt()]
        }

    }

    private fun getHundreds(input: String): String {
        val number = input[0].digitToInt()
        val hundred = mapOfUniqueNumbers[number]

        val result = input.toCharArray(1, 3)
        val tens = result.first().digitToInt() > 0
        val output =
            if (hundred.isNullOrEmpty()) "" else hundred + " " + number.asSuffix(Numbers.HUNDRED.asText) + " "
        return if (tens) output + getTens(input.substring(1, 3))
        else output + mapOfUniqueNumbers[result.last().digitToInt()]
    }

    private fun getThousands(input: String): String {
        return convertToText(input, (input.toInt() / THOUSAND), Numbers.THOUSAND.asText)
    }

    private fun getMillions(input: String): String {
        return convertToText(input, input.toInt() / MILLION, Numbers.MILLION.asText)
    }


    private fun convertToText(input: String, number: Int, suffix: String): String {

        return if (mapOfUniqueNumbers.contains(number)) {
            val uniquesOutput = mapOfUniqueNumbers[number] + " " + number.asSuffix(suffix) + " "
            return when {
                number < 10 -> {
                    when (suffix) {
                        Numbers.MILLION.asText -> {
                            uniquesOutput + getThousands(input.substring(1, 7))
                        }
                        else -> {
                            uniquesOutput + getHundreds(input.substring(1, 4))
                        }
                    }
                }
                else -> {
                    if (suffix == Numbers.MILLION.asText) {
                        uniquesOutput + getThousands(input.substring(2, 8))
                    } else {
                        uniquesOutput + getHundreds(input.substring(2, 5))
                    }
                }
            }
        } else when (number.asNumbers()) {
            Numbers.HUNDRED -> {
                when (suffix) {
                    Numbers.MILLION.asText -> {
                        getHundreds(number.toString()) + " " + (number / 100).asSuffix(suffix) + " " + getThousands(
                            input.substring(3, 9)
                        )
                    }
                    else -> {
                        getHundreds(number.toString()) + " " + (number / 100).asSuffix(suffix) + " " + getHundreds(
                            input.substring(3, 6)
                        )
                    }
                }
            }
            Numbers.TENS -> {
                when (suffix) {
                    Numbers.MILLION.asText -> {
                        getTens(number.toString()) + " " + (number / 10).asSuffix(suffix) + " " + getThousands(
                            input.substring(2, 8)
                        )
                    }
                    else -> {
                        getTens(number.toString()) + " " + (number / 10).asSuffix(suffix) + " " + getHundreds(
                            input.substring(3, 6)
                        )
                    }
                }
            }
            else -> ""
        }

    }

    private val mapOfUniqueNumbers: MutableMap<Int, String> = mutableMapOf(
        Pair(0, ""),
        Pair(1, "one"),
        Pair(2, "two"),
        Pair(3, "three"),
        Pair(4, "four"),
        Pair(5, "five"),
        Pair(6, "six"),
        Pair(7, "seven"),
        Pair(8, "eight"),
        Pair(9, "nine"),
        Pair(10, "ten"),
        Pair(11, "elven"),
        Pair(12, "twelve")
    )
    private val mapOfTens = mutableMapOf(
        Pair(2, "twen"),
        Pair(3, "thir"),
        Pair(4, "four"),
        Pair(5, "fif"),
        Pair(6, "six"),
        Pair(7, "seven"),
        Pair(8, "eigh"),
        Pair(9, "nine")
    )

}


fun main() {
    print("enter number:")
    val reader = Scanner(System.`in`)
    val input = reader.nextInt()
    val mapper = NumberMapper()
    val output = mapper(input)
    print(output)


}

const val MILLION = 1000000
const val THOUSAND = 1000