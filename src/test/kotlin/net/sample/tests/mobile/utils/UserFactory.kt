package net.sample.tests.mobile.utils

import net.sample.tests.mobile.entities.User
import java.lang.IllegalStateException

class UserFactory {
    companion object {
        fun getTestUser(): User {
            return User("mytest1082@gmail.com", "mytestaccount1082", "4043233556")
        }

        fun getUserByName(name: String): User {
            return when (name) {
                "Test" -> {
                    getTestUser()
                }
                else -> {
                    throw IllegalStateException("User not found")
                }
            }

        }
    }
}