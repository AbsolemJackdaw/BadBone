package subaraki.badbone.mod;

import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import subaraki.badbone.BadBone;
import subaraki.badbone.items.GlassesItem;
import subaraki.badbone.registry.BadBoneEffects;

@Mod(BadBone.MODID)
public class BadBoneForge {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BadBone.MODID);
    public static final RegistryObject<Item> GLASSES = ITEMS.register("glasses", GlassesItem::new);

    public BadBoneForge() {
        BadBoneEffects.EFFECTS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());

        ModLoadingContext modLoadingContext = ModLoadingContext.get();
        modLoadingContext.registerConfig(ModConfig.Type.SERVER, ConfigData.SERVER_SPEC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(BadBoneForge::registerCreativeTabs);
    }

    public static void registerCreativeTabs(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES)
            event.accept(GLASSES);
    }
}
