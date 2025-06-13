package com.example.agenda_contatos_atualizado.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.example.agenda_contatos_atualizado.data.model.Contact
import com.example.agenda_contatos_atualizado.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactItem(
    contact: Contact,
    onContactClick: (Contact) -> Unit,
    onEditClick: (Contact) -> Unit,
    onDeleteClick: (Contact) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onContactClick(contact) },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Foto do contato
            AsyncImage(
                model = contact.photoUri ?: "",
                contentDescription = "Foto de ${contact.name}",
                modifier = Modifier
                    .size(60.dp),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.ic_person),
                error = painterResource(id = R.drawable.ic_person)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Informações do contato
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = contact.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = contact.phone,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = contact.email,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            // Botões de ação
            Row {
                IconButton(onClick = { onEditClick(contact) }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar contato"
                    )
                }
                IconButton(onClick = { onDeleteClick(contact) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Excluir contato"
                    )
                }
            }
        }
    }
} 