package com.misanthropy.collections_of_optimizations.core;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.toml.TomlParser;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.fml.loading.LoadingModList;
import net.minecraftforge.fml.loading.moddiscovery.ModFileInfo;
import net.minecraftforge.forgespi.locating.IModFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class McreatorVariableDefaults {

    private static final Logger LOGGER = LogManager.getLogger("collections_of_optimizations");

    private static final String CONFIG_FILE = "collections_of_optimizations-common.toml";
    private static final String MASTER_KEY = "general.enabled";
    private static final String ENABLED_KEY = "mcreator.shareDefaultPlayerVariables";

    private static final String NETWORK_PACKAGE = ".network";
    private static final String VARIABLES_FILE = "$PlayerVariables.class";

    static final class Entry {

        final int index;
        final String packagePrefix;
        final String binaryName;
        final String internalName;

        private volatile Object shared;

        Entry(int index, String packageName, String binaryName) {
            this.index = index;
            this.packagePrefix = packageName + '.';
            this.binaryName = binaryName;
            this.internalName = binaryName.replace('.', '/');
        }

        private synchronized Object create() {
            Object existing = shared;
            if (existing != null) {
                return existing;
            }
            try {
                Object created = Class.forName(binaryName, true, McreatorVariableDefaults.class.getClassLoader())
                        .getDeclaredConstructor()
                        .newInstance();
                shared = created;
                return created;
            } catch (ReflectiveOperationException exception) {
                throw new IllegalStateException("Could not build the shared default for " + binaryName, exception);
            }
        }
    }

    private static volatile Entry[] entries = new Entry[0];

    private McreatorVariableDefaults() {
    }

    static Entry[] entries() {
        return entries;
    }

    public static Object shared(int index) {
        Entry entry = entries[index];
        Object instance = entry.shared;
        return instance != null ? instance : entry.create();
    }

    static void scan() {
        if (!enabled()) {
            return;
        }
        List<Entry> found = new ArrayList<>();
        for (ModFileInfo info : LoadingModList.get().getModFiles()) {
            try {
                collect(info.getFile(), found);
            } catch (Throwable throwable) {
                LOGGER.warn("Could not inspect {} for MCreator player variables.", info.moduleName(), throwable);
            }
        }
        entries = found.toArray(new Entry[0]);
        if (!found.isEmpty()) {
            LOGGER.info("Sharing the default player variables object for {} MCreator mod(s).", found.size());
        }
    }

    private static void collect(IModFile file, List<Entry> found) {
        Set<String> packages = file.getSecureJar().getPackages();
        for (String packageName : packages) {
            if (!packageName.endsWith(NETWORK_PACKAGE)) {
                continue;
            }
            Path directory = file.findResource(packageName.replace('.', '/'));
            if (directory == null || !Files.isDirectory(directory)) {
                continue;
            }
            List<Path> children;
            try (var stream = Files.list(directory)) {
                children = stream.toList();
            } catch (Exception exception) {
                continue;
            }
            for (Path child : children) {
                String fileName = child.getFileName().toString();
                if (!fileName.endsWith(VARIABLES_FILE)) {
                    continue;
                }
                String modPackage = packageName.substring(0, packageName.length() - NETWORK_PACKAGE.length());
                String binaryName = packageName + '.' + fileName.substring(0, fileName.length() - ".class".length());
                found.add(new Entry(found.size(), modPackage, binaryName));
            }
        }
    }

    private static boolean enabled() {
        try {
            Path path = FMLPaths.CONFIGDIR.get().resolve(CONFIG_FILE);
            if (!Files.exists(path)) {
                return true;
            }
            try (Reader reader = Files.newBufferedReader(path)) {
                CommentedConfig config = new TomlParser().parse(reader);
                return flag(config, MASTER_KEY) && flag(config, ENABLED_KEY);
            }
        } catch (Throwable throwable) {
            LOGGER.warn("Could not read {} this early, leaving the MCreator patch on.", CONFIG_FILE, throwable);
            return true;
        }
    }

    private static boolean flag(CommentedConfig config, String path) {
        Object value = config.get(path);
        return !(value instanceof Boolean set) || set;
    }
}
