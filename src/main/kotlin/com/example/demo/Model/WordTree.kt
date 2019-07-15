package com.example.demo.Model

class WordTree constructor(word: Word)
{
    val data = word

    var level = 1

    var parent: WordTree? = null

    val children = ArrayList<WordTree>()

    fun addChild(child: Word)
    {
        val childNode = WordTree(child)
        childNode.parent = this
        this.children.add(childNode)
        childNode.level = this.level + 1
    }
}