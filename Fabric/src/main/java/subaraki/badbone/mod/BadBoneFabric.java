package subaraki.badbone.mod;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import subaraki.badbone.BadBone;
import subaraki.badbone.effects.*;
import subaraki.badbone.events.PlayerEvents;
import subaraki.badbone.items.CommonGlassesItem;

public class BadBoneFabric implements ModInitializer {
    public static final MobEffect CHRONO = Registry.register(Registry.MOB_EFFECT, new ResourceLocation(BadBone.MODID, "chrono"), new EffectChronophobia());
    public static final MobEffect BLIND = Registry.register(Registry.MOB_EFFECT, new ResourceLocation(BadBone.MODID, "eye"), new EffectBlind());
    public static final MobEffect ARTHRITIS = Registry.register(Registry.MOB_EFFECT, new ResourceLocation(BadBone.MODID, "arthritis"), new EffectArthritis());
    public static final MobEffect KNEE_HURT = Registry.register(Registry.MOB_EFFECT, new ResourceLocation(BadBone.MODID, "knee"), new EffectWeakKnees());
    public static final MobEffect BACK_HURT = Registry.register(Registry.MOB_EFFECT, new ResourceLocation(BadBone.MODID, "hurt"), new EffectBackpain());
    private static final Item.Properties PROPERTIES = new FabricItemSettings().equipmentSlot(stack -> EquipmentSlot.HEAD).tab(CreativeModeTab.TAB_TOOLS).stacksTo(1);
    public static final Item GLASSES = Registry.register(Registry.ITEM, new ResourceLocation(BadBone.MODID, "glasses"), new CommonGlassesItem(PROPERTIES));
    public static ModConfig config;

    @Override
    public void onInitialize() {
        AutoConfig.register(ModConfig.class, Toml4jConfigSerializer::new);
        config = AutoConfig.getConfigHolder(ModConfig.class).get();
        PlayerEvents.registerEvents();
    }
}
