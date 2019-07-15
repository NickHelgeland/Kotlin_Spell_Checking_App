package com.example.demo.Model

class Word constructor(word: String)
{
    var probability: Int = 0

    var value = word

    var valid: Boolean = false

    var wordBefore = HashMap<String, Int>()

    var wordAfter = HashMap<String, Int>()

    fun incrementProbability()
    {
        probability++
    }
}