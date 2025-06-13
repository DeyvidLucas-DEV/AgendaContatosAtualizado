package com.example.agenda_contatos_atualizado.ui

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.agenda_contatos_atualizado.data.database.ContactDatabase
import com.example.agenda_contatos_atualizado.data.model.Contact
import com.example.agenda_contatos_atualizado.data.repository.ContactRepository
import com.example.agenda_contatos_atualizado.ui.viewmodel.ContactViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ContactViewModelTest {
    private lateinit var db: ContactDatabase
    private lateinit var repository: ContactRepository
    private lateinit var viewModel: ContactViewModel

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<android.content.Context>()
        db = Room.inMemoryDatabaseBuilder(context, ContactDatabase::class.java).allowMainThreadQueries().build()
        repository = ContactRepository(db.contactDao())
        viewModel = ContactViewModel(repository)
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun addContact_emitsInFlow() = runTest {
        val contact = Contact(name = "B", phone="1", email="b@c.com", address="", city="", state="", cep="")
        viewModel.addContact(contact)
        val list = viewModel.contacts.first()
        assertEquals(1, list.size)
        assertEquals("B", list[0].name)
    }
}
