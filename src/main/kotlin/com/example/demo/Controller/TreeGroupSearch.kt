package com.example.demo.Controller

import com.example.demo.Model.Word
import com.example.demo.Model.WordTree
import java.lang.StringBuilder

class TreeGroupSearch
{
    val listOfMatches = ArrayList<Word>()

    fun getLowerLevelMatches(tree: WordTree, word: WordTree)
    {
        if (!tree.children.isEmpty())
        {
            for (child in tree.children)
            {
                if (checkLetterSimilarityPercentage(child.data.value, word.data.value) > .3
                        && child.data.value.length > 2)
                {
                    getLowerLevelMatches(child, word)
                }
                else if (child.data.value.length < 3
                        && checkLetterSimilarityPercentage(child.data.value, word.data.value) > 0)
                {
                    getLowerLevelMatches(child, word)
                }
                if (checkSimilarities(word.data.value, doBigramAnalysis(child.data.value, word.data.value))
                        && child.level > word.level)
                {
                    listOfMatches.add(child.data)
                }
            }
        }
    }

    fun getUpperLevelMatches(tree: WordTree, word: WordTree)
    {
        if (word.level != 1 && word.level > 2)
        {
            recurseOnUpperLevels(tree, word, 2)
        }
        else if (word.level == 2)
        {
            recurseOnUpperLevels(tree, word, 1)
        }
    }

    fun getOnLevelMatches(tree: WordTree, word: WordTree)
    {
        for (child in tree.children)
        {
            if (child.level < word.level)
            {
                getOnLevelMatches(child, word)
            }
            if (child.level == word.level)
            {
                val similarities = doBigramAnalysis(child.data.value, word.data.value)
                if (checkSimilarities(word.data.value, similarities) && !word.data.value.equals(child.data.value))
                {
                    listOfMatches.add(child.data)
                }
            }
        }
    }

    private fun recurseOnUpperLevels(tree: WordTree, word: WordTree, levelsToRecurse: Int)
    {
        for (child in tree.children)
        {
            if (child.level < word.level)
            {
                recurseOnUpperLevels(child, word, levelsToRecurse)
            }
            if (child.level >= (word.level - levelsToRecurse) && child.level < word.level)
            {
                if (doBigramAnalysis(child.data.value, word.data.value) >= (child.data.value.length / 2))
                {
                    listOfMatches.add(child.data)
                }
                else if (child.data.value.equals(word.data.value.get(0)))
                {
                    listOfMatches.add(child.data)
                }
            }
        }
    }

    fun doBigramAnalysis(newString: String, oldString: String): Int
    {
        val bigramList1 = ArrayList<String>()
        val bigramList2 = ArrayList<String>()
        var similarities = 0

        for (i in 0 until (newString.length - 1))
        {
            bigramList1.add(StringBuilder().append(newString.get(i)).append(newString.get(i+1)).toString())
        }
        for (i in 0 until (oldString.length - 1))
        {
            bigramList2.add(StringBuilder().append(oldString.get(i)).append(oldString.get(i+1)).toString())
        }
        for (bigram in bigramList2)
        {
            if (bigramList1.contains(bigram))
            {
                similarities++
            }
        }

        return similarities
    }

    private fun checkSimilarities(word: String, similarities: Int): Boolean
    {
        var results = false

        if (similarities >= (word.length / 2))
        {
            results = true
        }

        return results
    }

    fun clearMatches()
    {
        listOfMatches.clear()
    }

    private fun checkLetterSimilarityPercentage(newString: String, oldString: String): Double
    {
        var matches  = 0.0
        var similarityPercentage: Double

        for (letter in oldString)
        {
            if(newString.contains(letter))
            {
                matches++
            }
        }

        similarityPercentage = (matches / oldString.length)

        return similarityPercentage
    }
}