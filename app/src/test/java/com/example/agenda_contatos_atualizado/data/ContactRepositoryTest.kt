package com.example.agenda_contatos_atualizado.data

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.agenda_contatos_atualizado.data.database.ContactDatabase
import com.example.agenda_contatos_atualizado.data.model.Contact
import com.example.agenda_contatos_atualizado.data.repository.ContactRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ContactRepositoryTest {
    private lateinit var db: ContactDatabase
    private lateinit var repository: ContactRepository

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<android.content.Context>()
        db = Room.inMemoryDatabaseBuilder(context, ContactDatabase::class.java).allowMainThreadQueries().build()
        repository = ContactRepository(db.contactDao())
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun insertAndGetContact() = runTest {
        val contact = Contact(name = "A", phone = "123", email = "a@b.com", address="", city="", state="", cep="")
        val id = repository.insertContact(contact)
        val loaded = repository.getContactById(id)!!
        assertEquals("A", loaded.name)
    }
}
