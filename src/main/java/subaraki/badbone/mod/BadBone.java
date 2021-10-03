package subaraki.badbone.mod;

import net.minecraftforge.fml.common.Mod;
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
        BadBoneEffects.EFFECTS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BadBoneItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());

    }
}
