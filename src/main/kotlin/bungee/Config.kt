@file:JvmName("Kotlin4Bungee")
@file:JvmMultifileClass

package hazae41.minecraft.kotlin.bungee

import hazae41.minecraft.kotlin.ex
import hazae41.minecraft.kotlin.get
import net.md_5.bungee.config.Configuration
import java.io.File
import kotlin.reflect.KProperty

val configProvider get() = BungeeConfigurationProvider.getProvider(BungeeYaml::class.java)

@Deprecated("Use loadConfig() instead")
fun BungeePlugin.load(file: File, resource: String = file.nameWithoutExtension + "/bungee.yml") = loadConfig(file, resource)

fun BungeePlugin.loadConfig(
        file: File, resource: String = file.name
): Configuration {
    saveResource(resource, file)
    return configProvider.load(file)
            ?: throw ex("Could not load ${file.path}")
}

fun BungeePlugin.saveResource(resource: String, file: File) {
    if (file.exists()) return
    file.parentFile.mkdirs()
    file.createNewFile()
    getResourceAsStream(resource).copyTo(file.outputStream())
}

@Deprecated("Use saveConfig() instead")
fun BungeePlugin.save(config: BungeeConfiguration, file: File) = saveConfig(config, file)

fun saveConfig(config: BungeeConfiguration, file: File) = configProvider.save(config, file)

fun BungeeConfiguration.section(path: String) = getSection(path)
val BungeeConfiguration.sections get() = keys.map { section(it) }

fun BungeePlugin.init(vararg configs: ConfigFile) {
    configs.forEach { it.init(this) }
}

abstract class Config(open var autoSave: Boolean = true) {

    private lateinit var _config: Configuration
    var config: Configuration
        set(value) {
            _config = value
        }
        get() {
            if (!::_config.isInitialized) reload()
            return _config
        }

    open val sections get() = config.sections

    abstract fun reload()
    abstract fun save()

    open operator fun contains(key: String) = config.contains(key)
    open operator fun get(key: String) = config[key]
    open operator fun set(key: String, value: Any){
        config.set(key, value)
        if(autoSave) save()
    }

    open inner class any(val path: String, val def: Any? = null) {
        operator fun getValue(ref: Any?, prop: KProperty<*>) = config.get(path, def)
        operator fun setValue(ref: Any?, prop: KProperty<*>, value: Any?) {
            config.set(path, value); if (autoSave) save()
        }
    }

    open inner class list(val path: String, val def: List<Any> = emptyList()) {
        operator fun getValue(ref: Any?, prop: KProperty<*>): List<*> = config.getList(path)
        operator fun setValue(ref: Any?, prop: KProperty<*>, value: List<Any>) {
            config.set(path, value); if (autoSave) save()
        }
    }

    open inner class boolean(val path: String, val def: Boolean = false) {
        operator fun getValue(ref: Any?, prop: KProperty<*>): Boolean = config.getBoolean(path, def)
        operator fun setValue(ref: Any?, prop: KProperty<*>, value: Boolean) {
            config.set(path, value); if (autoSave) save()
        }
    }

    open inner class booleanList(val path: String) {
        operator fun getValue(ref: Any?, prop: KProperty<*>): List<Boolean> = config.getBooleanList(path)
        operator fun setValue(ref: Any?, prop: KProperty<*>, value: List<Boolean>) {
            config.set(path, value); if (autoSave) save()
        }
    }

    open inner class string(val path: String, val def: String = "") {
        operator fun getValue(ref: Any?, prop: KProperty<*>): String = config.getString(path, def)
        operator fun setValue(ref: Any?, prop: KProperty<*>, value: String) {
            config.set(path, value); if (autoSave) save()
        }
    }

    open inner class stringList(val path: String) {
        operator fun getValue(ref: Any?, prop: KProperty<*>): List<String> = config.getStringList(path)
        operator fun setValue(ref: Any?, prop: KProperty<*>, value: List<String>) {
            config.set(path, value); if (autoSave) save()
        }
    }

    open inner class int(val path: String, val def: Int = 0) {
        operator fun getValue(ref: Any?, prop: KProperty<*>): Int = config.getInt(path, def)
        operator fun setValue(ref: Any?, prop: KProperty<*>, value: Int) {
            config.set(path, value); if (autoSave) save()
        }
    }

    open inner class intList(val path: String) {
        operator fun getValue(ref: Any?, prop: KProperty<*>): List<Int> = config.getIntList(path)
        operator fun setValue(ref: Any?, prop: KProperty<*>, value: List<Int>) {
            config.set(path, value); if (autoSave) save()
        }
    }

    open inner class long(val path: String, val def: Long = 0L) {
        operator fun getValue(ref: Any?, prop: KProperty<*>): Long = config.getLong(path, def)
        operator fun setValue(ref: Any?, prop: KProperty<*>, value: Long) {
            config.set(path, value); if (autoSave) save()
        }
    }

    open inner class longList(val path: String) {
        operator fun getValue(ref: Any?, prop: KProperty<*>): List<Long> = config.getLongList(path)
        operator fun setValue(ref: Any?, prop: KProperty<*>, value: List<Long>) {
            config.set(path, value); if (autoSave) save()
        }
    }

    open inner class double(val path: String, val def: Double = 0.0) {
        operator fun getValue(ref: Any?, prop: KProperty<*>): Double = config.getDouble(path, def)
        operator fun setValue(ref: Any?, prop: KProperty<*>, value: Double) {
            config.set(path, value); if (autoSave) save()
        }
    }

    open inner class doubleList(val path: String) {
        operator fun getValue(ref: Any?, prop: KProperty<*>): List<Double> = config.getDoubleList(path)
        operator fun setValue(ref: Any?, prop: KProperty<*>, value: List<Double>) {
            config.set(path, value); if (autoSave) save()
        }
    }

    open inner class byte(val path: String, val def: Byte = 0) {
        operator fun getValue(ref: Any?, prop: KProperty<*>): Byte = config.getByte(path, def)
        operator fun setValue(ref: Any?, prop: KProperty<*>, value: Byte) {
            config.set(path, value); if (autoSave) save()
        }
    }

    open inner class byteList(val path: String) {
        operator fun getValue(ref: Any?, prop: KProperty<*>): List<Byte> = config.getByteList(path)
        operator fun setValue(ref: Any?, prop: KProperty<*>, value: List<Byte>) {
            config.set(path, value); if (autoSave) save()
        }
    }

    open inner class float(val path: String, val def: Float = 0f) {
        operator fun getValue(ref: Any?, prop: KProperty<*>): Float = config.getFloat(path, def)
        operator fun setValue(ref: Any?, prop: KProperty<*>, value: Float) {
            config.set(path, value); if (autoSave) save()
        }
    }

    open inner class floatList(val path: String) {
        operator fun getValue(ref: Any?, prop: KProperty<*>): List<Float> = config.getFloatList(path)
        operator fun setValue(ref: Any?, prop: KProperty<*>, value: List<Float>) {
            config.set(path, value); if (autoSave) save()
        }
    }

    open inner class short(val path: String, val def: Short = 0) {
        operator fun getValue(ref: Any?, prop: KProperty<*>): Short = config.getShort(path, def)
        operator fun setValue(ref: Any?, prop: KProperty<*>, value: Short) {
            config.set(path, value); if (autoSave) save()
        }
    }

    open inner class shortList(val path: String) {
        operator fun getValue(ref: Any?, prop: KProperty<*>): List<Short> = config.getShortList(path)
        operator fun setValue(ref: Any?, prop: KProperty<*>, value: List<Short>) {
            config.set(path, value); if (autoSave) save()
        }
    }

    open inner class char(val path: String, val def: Char = '\u0000') {
        operator fun getValue(ref: Any?, prop: KProperty<*>): Char = config.getChar(path, def)
        operator fun setValue(ref: Any?, prop: KProperty<*>, value: Char) {
            config.set(path, value); if (autoSave) save()
        }
    }

    open inner class charList(val path: String) {
        operator fun getValue(ref: Any?, prop: KProperty<*>): List<Char> = config.getCharList(path)
        operator fun setValue(ref: Any?, prop: KProperty<*>, value: List<Char>) {
            config.set(path, value); if (autoSave) save()
        }
    }

    open inner class section(val path: String) {
        operator fun getValue(ref: Any?, prop: KProperty<*>): Configuration? = config.getSection(path)
        operator fun setValue(ref: Any?, prop: KProperty<*>, value: Configuration?) {
            config.set(path, value); if (autoSave) save()
        }
    }
}

open class ConfigFile(autoSave: Boolean = true) : Config(autoSave) {

    lateinit var file: File

    constructor(
            file: File, autoSave: Boolean = true
    ) : this(autoSave) {
        this.file = file
    }

    private lateinit var path: String

    constructor(
            path: String, autoSave: Boolean = true
    ) : this(autoSave) {
        if (!path.endsWith(".yml"))
            this.path = "$path.yml"
        else this.path = path
    }

    fun init(plugin: BungeePlugin, resource: String = path) {
        if (::file.isInitialized)
            throw ex("Config is already initialized")
        file = plugin.dataFolder[path]
        if (resource.endsWith(".yml"))
            plugin.saveResource(resource, file)
        else
            plugin.saveResource("$resource.yml", file)
    }

    override fun reload() {
        if (!::file.isInitialized) throw ex("Config is not initialized")
        config = configProvider.load(file)
        ?: throw ex("Could not load ${file.name}")
    }

    override fun save() = saveConfig(config, file)
}

open class ConfigSection(
    var parent: Config, var path: String, autoSave: Boolean = true
) : Config(autoSave) {

    override fun reload() {
        config = parent.config.getSection(path)
        ?: throw ex("Could not load $path from ${parent.javaClass.name}")
    }

    override fun save() {
        parent.config.set(path, config)
        if(parent.autoSave) parent.save()
    }
}