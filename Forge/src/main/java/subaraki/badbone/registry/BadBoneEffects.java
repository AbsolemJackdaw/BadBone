package subaraki.badbone.registry;

import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import subaraki.badbone.BadBone;
import subaraki.badbone.effects.*;

public class BadBoneEffects {

    public static DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, BadBone.MODID);
    public static final RegistryObject<MobEffect> BACK_HURT = EFFECTS.register("hurt", EffectBackpain::new);
    public static final RegistryObject<MobEffect> KNEE_HURT = EFFECTS.register("knee", EffectWeakKnees::new);
    public static final RegistryObject<MobEffect> ARTHRITIS = EFFECTS.register("arthritis", EffectArthritis::new);
    public static final RegistryObject<MobEffect> BLIND = EFFECTS.register("eye", EffectBlind::new);
    public static final RegistryObject<MobEffect> CHRONO = EFFECTS.register("chrono", EffectChronophobia::new);

}