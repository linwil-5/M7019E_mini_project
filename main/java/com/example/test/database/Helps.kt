package com.example.test.database

import com.example.test.model.Help

class Helps {

    val list = mutableListOf<Help>()

    init {
        list.add(
            Help(
                "Servers aren't showing at server status",
                "Are you sure the servers are reachable?"
            )
        )
        list.add(
            Help(
                "Do I have to add each server manually",
                "No, as long it's found by the api it should be listed by recycler view as an item"
            )
        )
        list.add(
            Help(
                "Who/whom made this",
                "This is made by Emil and William"
            )
        )
    }
}