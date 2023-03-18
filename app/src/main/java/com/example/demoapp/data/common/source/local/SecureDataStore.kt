package com.example.demoapp.data.common.source.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.demoapp.data.common.DATE_STORE_NAME
import com.example.demoapp.data.pokemon.local.model.CachedPokemon
import com.example.demoapp.util.EncryptionUtils
import com.example.demoapp.util.toGson
import com.example.demoapp.util.toObjectOrThrow
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SecureDataStore @Inject constructor(
    private val encryptionUtils: EncryptionUtils,
    @ApplicationContext private val context: Context
) {

    private val Context._dataStore: DataStore<Preferences> by preferencesDataStore(name = DATE_STORE_NAME)

    private val dataStore: DataStore<Preferences> = context._dataStore

    val accessTokenDecrypted = mapDecrypted(ACCESS_TOKEN_KEY)

    val pokemonDetailsDecrypted = mapDecrypted(POKEMON_DETAILS_KEY)
        .map { it.toObjectOrThrow(CachedPokemon::class.java) }

    val pokemonDetailsEncrypted = mapEncrypted(POKEMON_DETAILS_KEY)

    suspend fun saveAccessToken(token: String) =
        saveEncrypted(ACCESS_TOKEN_KEY, token)

    suspend fun savePokemonInfo(pokemon: CachedPokemon) =
        saveEncrypted(POKEMON_DETAILS_KEY, pokemon.toGson())

    private suspend fun saveEncrypted(key: Preferences.Key<String>, value: Any) {
        dataStore.edit {
            val encryptedValue = value.toString()
                .runCatching { encryptionUtils.encrypt(this) }
                .getOrDefault("")
            it[key] = encryptedValue
        }
    }

    private fun mapDecrypted(
        key: Preferences.Key<String>,
        defaultValue: String = ""
    ): Flow<String> = dataStore.data
        .catch { emit(emptyPreferences()) }
        .map {
            val value = it[key]
            if (value != null) encryptionUtils.decrypt(value) else defaultValue
        }
        .catch { emit(defaultValue) }
        .distinctUntilChanged()

    private fun mapEncrypted(
        key: Preferences.Key<String>,
        defaultValue: String = ""
    ): Flow<String> = dataStore.data
        .catch { emit(emptyPreferences()) }
        .map { it[key] ?: defaultValue } // Get the saved ENCRYPTED [key] value.
        .distinctUntilChanged()

    suspend fun clearData() {
        dataStore.edit { it.clear() }
    }

    /**
     * All values must be saved as String in datastore for encryption purposes.
     */
    companion object {
        private val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
        private val POKEMON_DETAILS_KEY = stringPreferencesKey("pokemon_details")
    }
}
