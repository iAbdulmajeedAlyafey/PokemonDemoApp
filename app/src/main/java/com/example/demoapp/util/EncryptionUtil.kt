package com.example.demoapp.util

import android.annotation.SuppressLint
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

/**
 * This class uses AES encryption algorithm.
 */
class EncryptionUtils {

  private val keyValue = byteArrayOf(
    'c'.code.toByte(),
    'o'.code.toByte(),
    'd'.code.toByte(),
    'i'.code.toByte(),
    'n'.code.toByte(),
    'g'.code.toByte(),
    'a'.code.toByte(),
    'f'.code.toByte(),
    'f'.code.toByte(),
    'a'.code.toByte(),
    'i'.code.toByte(),
    'r'.code.toByte(),
    's'.code.toByte(),
    'c'.code.toByte(),
    'o'.code.toByte(),
    'm'.code.toByte()
  )

  @Throws(Exception::class)
  fun encrypt(cleartext : String) : String {
    val rawKey = rawKey
    val result = encrypt(rawKey, cleartext.toByteArray())
    return toHex(result)
  }

  @Throws(Exception::class)
  fun decrypt(encrypted : String) : String {
    val enc = toByte(encrypted)
    val result = decrypt(enc)
    return String(result)
  }

  @get:Throws(Exception::class)
  private val rawKey : ByteArray
    get() {
      val key : SecretKey = SecretKeySpec(keyValue, Companion.ENCRYPTION_ALGORITHM)
      return key.encoded
    }

  @SuppressLint("GetInstance")
  @Throws(Exception::class)
  private fun encrypt(raw : ByteArray, clear : ByteArray) : ByteArray {
    val secretKey : SecretKey = SecretKeySpec(raw, Companion.ENCRYPTION_ALGORITHM)
    val cipher = Cipher.getInstance(Companion.ENCRYPTION_ALGORITHM)
    cipher.init(Cipher.ENCRYPT_MODE, secretKey)
    return cipher.doFinal(clear)
  }

  @SuppressLint("GetInstance")
  @Throws(Exception::class)
  private fun decrypt(encrypted : ByteArray) : ByteArray {
    val secretKey : SecretKey = SecretKeySpec(keyValue, Companion.ENCRYPTION_ALGORITHM)
    val cipher = Cipher.getInstance(Companion.ENCRYPTION_ALGORITHM)
    cipher.init(Cipher.DECRYPT_MODE, secretKey)
    return cipher.doFinal(encrypted)
  }

  private fun toByte(hexString : String) : ByteArray {
    val len = hexString.length / 2
    val result = ByteArray(len)
    for (i in 0 until len) result[i] = Integer.valueOf(
      hexString.substring(2 * i, 2 * i+2),
      16
    ).toByte()
    return result
  }

  private fun toHex(buf : ByteArray?) : String {
    if (buf == null) return ""
    val result = StringBuffer(2 * buf.size)
    for (b in buf) {
      appendHex(result, b)
    }
    return result.toString()
  }

  private fun appendHex(sb : StringBuffer, b : Byte) {
    sb.append(HEX[b shr 4 and 0x0f]).append(HEX[b and 0x0f])
  }

  private infix fun Byte.shr(bitCount : Int) : Int = toInt().shr(bitCount)

  private infix fun Byte.and(bitCount : Int) : Int = toInt().and(bitCount)

  companion object {
    private const val ENCRYPTION_ALGORITHM = "AES"
    private const val HEX = "0123456789ABCDEF"
  }
}
