package com.example.agenda_contatos_atualizado

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.agenda_contatos_atualizado.data.database.ContactDatabase
import com.example.agenda_contatos_atualizado.data.repository.ContactRepository
import com.example.agenda_contatos_atualizado.ui.screens.ContactFormScreen
import com.example.agenda_contatos_atualizado.ui.screens.ContactListScreen
import com.example.agenda_contatos_atualizado.ui.theme.AgendaContatosAtualizadoTheme
import com.example.agenda_contatos_atualizado.ui.viewmodel.ContactViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = ContactDatabase.getDatabase(applicationContext)
        val repository = ContactRepository(database.contactDao())

        setContent {
            AgendaContatosAtualizadoTheme {
                val navController = rememberNavController()
                val viewModel: ContactViewModel = viewModel(
                    factory = ContactViewModel.ContactViewModelFactory(repository)
                )
                val contacts by viewModel.contacts.collectAsState()

                NavHost(navController = navController, startDestination = "contactList") {
                    composable("contactList") {
                        ContactListScreen(
                            contacts = contacts,
                            onAddContact = { navController.navigate("contactForm") },
                            onContactClick = { contact ->
                                navController.navigate("contactForm/${contact.id}")
                            },
                            onEditContact = { contact ->
                                navController.navigate("contactForm/${contact.id}")
                            },
                            onDeleteContact = { contact ->
                                viewModel.deleteContact(contact)
                            },
                            onSearchQueryChange = { query ->
                                viewModel.searchContacts(query)
                            }
                        )
                    }

                    composable("contactForm") {
                        ContactFormScreen(
                            onSave = { contact ->
                                viewModel.addContact(contact)
                                navController.popBackStack()
                            },
                            onNavigateBack = {
                                navController.popBackStack()
                            }
                        )
                    }

                    composable("contactForm/{contactId}") { backStackEntry ->
                        val contactId = backStackEntry.arguments?.getString("contactId")?.toLongOrNull()
                        val contact = contacts.find { it.id == contactId }
                        
                        ContactFormScreen(
                            contact = contact,
                            onSave = { updatedContact ->
                                viewModel.updateContact(updatedContact)
                                navController.popBackStack()
                            },
                            onNavigateBack = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}