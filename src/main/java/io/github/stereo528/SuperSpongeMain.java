package io.github.stereo528;

import net.fabricmc.api.ModInitializer;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SuperSpongeMain implements ModInitializer {

    public static Logger LOGGER = LogManager.getLogger();

    public static final String MOD_ID = "supersponge";
    public static final String MOD_NAME = "SuperSponge";

    @Override
    public void onInitialize() {
        log(Level.INFO, "Soak that stuff up man! Cmon!");
        SuperSpongeBlocks.init();

    }

    public static void log(Level level, String message){
        LOGGER.log(level, "["+MOD_NAME+"] " + message);
    }
}