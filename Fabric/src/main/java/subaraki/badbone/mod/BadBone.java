package subaraki.badbone.mod;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import subaraki.badbone.CommonConfig;
import subaraki.badbone.effects.*;
import subaraki.badbone.events.PlayerEvents;
import subaraki.badbone.items.CommonGlassesItem;

public class BadBone implements ModInitializer {
    public static final MobEffect CHRONO = Registry.register(Registry.MOB_EFFECT, new ResourceLocation(subaraki.badbone.BadBone.MODID, "chrono"), new EffectChronophobia());
    public static final MobEffect BLIND = Registry.register(Registry.MOB_EFFECT, new ResourceLocation(subaraki.badbone.BadBone.MODID, "eye"), new EffectBlind());
    public static final MobEffect ARTHRITIS = Registry.register(Registry.MOB_EFFECT, new ResourceLocation(subaraki.badbone.BadBone.MODID, "arthritis"), new EffectArthritis());
    public static final MobEffect KNEE_HURT = Registry.register(Registry.MOB_EFFECT, new ResourceLocation(subaraki.badbone.BadBone.MODID, "knee"), new EffectWeakKnees());
    public static final MobEffect BACK_HURT = Registry.register(Registry.MOB_EFFECT, new ResourceLocation(subaraki.badbone.BadBone.MODID, "hurt"), new EffectBackpain());
    private static final Item.Properties PROPERTIES = new FabricItemSettings().equipmentSlot(stack -> EquipmentSlot.HEAD).tab(CreativeModeTab.TAB_TOOLS).stacksTo(1);
    public static final Item GLASSES = Registry.register(Registry.ITEM, new ResourceLocation(subaraki.badbone.BadBone.MODID, "glasses"), new CommonGlassesItem(PROPERTIES));

    @Override
    public void onInitialize() {
        AutoConfig.register(ModConfig.class, Toml4jConfigSerializer::new);
        AutoConfig.getConfigHolder(ModConfig.class).registerSaveListener((configHolder, modConfig) -> {
            CommonConfig.frequencyHurt = modConfig.frequencyHurt;
            CommonConfig.frequencyKnee = modConfig.frequencyKnee;
            CommonConfig.frequencyArthritis = modConfig.frequencyArthritis;
            CommonConfig.frequencyEyes = modConfig.frequencyEyes;
            CommonConfig.chanceHurt = modConfig.chanceHurt;
            return InteractionResult.PASS;
        });
        PlayerEvents.registerEvents();
    }
}
