/*
 * Copyright (c) 2021/  3/ 23.  Created by Hashim Tahir
 */
package com.hashim.mapswithgeofencing.utils

class Event<T>(private val content: T) {

    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content

    override fun toString(): String {
        return "Event(content=$content,hasBeenHandled=$hasBeenHandled)"
    }

    companion object {

        fun <T> dataEvent(data: T?): Event<T>? {
            data?.let {
                return Event(it)
            }
            return null
        }

        fun messageEvent(message: String?): Event<String>? {
            message?.let {
                return Event(message)
            }
            return null
        }
    }


}