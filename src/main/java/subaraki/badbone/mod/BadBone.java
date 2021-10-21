package subaraki.badbone.mod;

import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import subaraki.badbone.items.BadBoneItems;
import subaraki.badbone.registry.BadBoneEffects;

@Mod(BadBone.MODID)
public class BadBone {
    public static final String MODID = "badbone";
    public static final Logger LOG = LogManager.getLogger();

    public BadBone() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ConfigData.CLIENT_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ConfigData.SERVER_SPEC);
        
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ConfigData::onLoad);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ConfigData::onReload);
        
        BadBoneEffects.EFFECTS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BadBoneItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
