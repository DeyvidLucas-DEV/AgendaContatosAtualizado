package com.example.agenda_contatos_atualizado.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.agenda_contatos_atualizado.data.model.Contact
import com.example.agenda_contatos_atualizado.ui.components.ContactItem
import com.example.agenda_contatos_atualizado.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactListScreen(
    contacts: List<Contact>,
    onAddContact: () -> Unit,
    onContactClick: (Contact) -> Unit,
    onEditContact: (Contact) -> Unit,
    onDeleteContact: (Contact) -> Unit,
    onSearchQueryChange: (String) -> Unit
) {
    var showDeleteDialog by remember { mutableStateOf<Contact?>(null) }
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.title_contact_list)) },
                actions = {
                    IconButton(onClick = onAddContact) {
                        Icon(Icons.Default.Add, stringResource(R.string.add_contact))
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Barra de pesquisa
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { 
                    searchQuery = it
                    onSearchQueryChange(it)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text(stringResource(R.string.search_contacts_placeholder)) },
                leadingIcon = {
                    Icon(Icons.Default.Search, stringResource(R.string.search))
                },
                singleLine = true
            )

            // Lista de contatos
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(contacts) { contact ->
                    ContactItem(
                        contact = contact,
                        onContactClick = onContactClick,
                        onEditClick = onEditContact,
                        onDeleteClick = { showDeleteDialog = contact }
                    )
                }
            }
        }
    }

    // Diálogo de confirmação de exclusão
    showDeleteDialog?.let { contact ->
        AlertDialog(
            onDismissRequest = { showDeleteDialog = null },
            title = { Text(stringResource(R.string.delete_contact_title)) },
            text = { Text(stringResource(R.string.delete_contact_message, contact.name)) },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDeleteContact(contact)
                        showDeleteDialog = null
                    }
                ) {
                    Text(stringResource(R.string.delete))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = null }) {
                    Text(stringResource(R.string.cancel))
                }
            }
        )
    }
} 
