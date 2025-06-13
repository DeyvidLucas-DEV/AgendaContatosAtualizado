package com.example.agenda_contatos_atualizado.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import com.example.agenda_contatos_atualizado.data.model.Contact
import com.example.agenda_contatos_atualizado.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactFormScreen(
    contact: Contact? = null,
    onSave: (Contact) -> Unit,
    onNavigateBack: () -> Unit
) {
    var name by remember { mutableStateOf(contact?.name ?: "") }
    var phone by remember { mutableStateOf(contact?.phone ?: "") }
    var email by remember { mutableStateOf(contact?.email ?: "") }
    var address by remember { mutableStateOf(contact?.address ?: "") }
    var city by remember { mutableStateOf(contact?.city ?: "") }
    var state by remember { mutableStateOf(contact?.state ?: "") }
    var cep by remember { mutableStateOf(contact?.cep ?: "") }
    var photoUri by remember { mutableStateOf(contact?.photoUri) }

    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { photoUri = it.toString() }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (contact == null) stringResource(R.string.new_contact) else stringResource(R.string.edit_contact)) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, stringResource(R.string.back))
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            val newContact = Contact(
                                id = contact?.id ?: 0,
                                name = name,
                                phone = phone,
                                email = email,
                                address = address,
                                city = city,
                                state = state,
                                cep = cep,
                                photoUri = photoUri
                            )
                            onSave(newContact)
                        }
                    ) {
                        Icon(Icons.Default.Save, stringResource(R.string.save))
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Foto do contato
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .padding(bottom = 16.dp)
            ) {
                AsyncImage(
                    model = photoUri ?: "",
                    contentDescription = "Foto do contato",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = R.drawable.ic_person),
                    error = painterResource(id = R.drawable.ic_person)
                )
                Button(
                    onClick = { launcher.launch("image/*") },
                    modifier = Modifier.align(Alignment.BottomEnd)
                ) {
                    Text(stringResource(R.string.change_photo))
                }
            }

            // Campos do formulário
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(stringResource(R.string.name)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text(stringResource(R.string.phone)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(stringResource(R.string.email)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                label = { Text(stringResource(R.string.address)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = city,
                    onValueChange = { city = it },
                    label = { Text(stringResource(R.string.city)) },
                    modifier = Modifier.weight(1f)
                )

                OutlinedTextField(
                    value = state,
                    onValueChange = { state = it },
                    label = { Text(stringResource(R.string.state)) },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = cep,
                onValueChange = { cep = it },
                label = { Text(stringResource(R.string.cep)) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
} 
