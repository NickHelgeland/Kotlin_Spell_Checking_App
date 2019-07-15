package com.example.demo.Controller

import com.example.demo.Model.Dictionary
import com.example.demo.Model.Word
import com.example.demo.Model.WordTree

class TreeManager
{
    fun addNewWord(word: Word, tree: WordTree)
    {
        if (tree != null && !tree.children.isEmpty())
        {
            if (tree.level == word.value.length)
            {
                var found = false
                for (child in tree.children)
                {
                    if (child.data.value.equals(word.value))
                    {
                        found = true
                        if (checkWordValidity(child.data.value))
                        {
                            child.data.incrementProbability()
                            child.data.valid = true
                        }
                    }
                }
                if (found == false)
                {
                    tree.addChild(word)
                }
            }
            else if (tree.level < word.value.length)
            {
                var match = false

                for (child in tree.children)
                {
                    if (beginsWith(child.data.value, word.value))
                    {
                        match = true
                        addNewWord(word, child)
                    }
                }
                if (match == false)
                {
                    addAllLettersOfWord(word, tree)
                }
            }
        }
        else if (tree != null && tree.children.isEmpty())
        {
            addAllLettersOfWord(word, tree)
        }
    }

    private fun checkWordValidity(word: String): Boolean
    {
        var wordValidity = false

        if (findWord(word, Dictionary.tree) != null)
        {
//            val find = findWord(word, Dictionary.tree)
            if (findWord(word, Dictionary.tree)!!.data.valid)
            {
                wordValidity = true
            }
        }

        return wordValidity
    }

    fun findWord(word: String, tree: WordTree): WordTree?
    {
        var match: WordTree? = null

        if (!tree.children.isEmpty())
        {
            for (child in tree.children)
            {
                if (child.data.value.equals(word))
                {
                    match = child
                }
            }

            if (match == null)
            {
                for (child in tree.children)
                {
                    if(word.startsWith(child.data.value))
                    {
                        match = findWord(word, child)
                    }
                }
            }
        }

        return match
    }

    private fun beginsWith(shortString: String, longString: String): Boolean
    {
        var match = false

        for (i in 0 until shortString.length)
        {
            match = (shortString[i] == longString[i])
        }

        return match
    }

    fun addAllLettersOfWord(word: Word, tree: WordTree)
    {
        if (tree.level < word.value.length)
        {
            var temp = ""
            for (i in 0 until tree.level)
            {
                temp += word.value[i]
            }
            tree.addChild(Word(temp))
            for (child in tree.children)
            {
                if (child.data.value == temp)
                {
                    addAllLettersOfWord(word, child)
                }
            }
        }
        else if (tree.level == word.value.length)
        {
            tree.addChild(word)
        }
    }
}