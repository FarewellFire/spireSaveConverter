import org.apache.commons.codec.binary.Base64
import java.io.File
import kotlin.experimental.xor

fun saveToJson(saveFilePath: String, jsonFilePath: String)
{
    val saveFile = File(saveFilePath)
    val jsonFile = File(jsonFilePath)

    val decoded = decodeSaveFile(saveFile)
    jsonFile.writeText(decoded.toString())
}

fun jsonToSave(saveFilePath: String, jsonFilePath: String)
{
    val saveFile = File(saveFilePath)
    val jsonFile = File(jsonFilePath)

    val encoded = encode(jsonFile.readText(), "key")
    saveFile.writeText(encoded)
}

private fun encode(s: String, key: String): String {
    return base64Encode(xorWithKey(s.toByteArray(), key.toByteArray()))
}

private fun xorWithKey(a: ByteArray, key: ByteArray): ByteArray {
    val out = ByteArray(a.size)
    for (i in a.indices) {
        out[i] = (a[i] xor key[i % key.size]) as Byte
    }
    return out
}

private fun base64Encode(bytes: ByteArray): String {
    return String(Base64.encodeBase64(bytes))
}

private fun base64Decode(s: String): ByteArray {
    return Base64.decodeBase64(s)
}

private fun decodeSaveFile(file: File): String? {
    val data = file.readText()
    return if (isObfuscated(data)) decode(data, "key") else data
}

private fun decode(s: String, key: String): String {
    return String(xorWithKey(base64Decode(s), key.toByteArray()))
}

private fun isObfuscated(data: String): Boolean {
    return !data.contains("{")
}