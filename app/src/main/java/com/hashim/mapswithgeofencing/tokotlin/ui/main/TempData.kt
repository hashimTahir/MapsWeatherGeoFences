package com.hashim.mapswithgeofencing.tokotlin.ui.main

class TempData {
    companion object {
        fun hGetTempCategroyList(): List<String> {
            val hList: MutableList<String> = mutableListOf<String>()
            hList.add("Fuel")
            hList.add("Restrauents")
            hList.add("Atms")
            hList.add("Hospitals")
            hList.add("Banks")
            return hList
        }
    }
}