package subaraki.badbone.platform;

import net.minecraft.world.effect.MobEffect;
import subaraki.badbone.mod.BadBoneFabric;

public class EffectHelper implements IEffectHelper {
    @Override
    public MobEffect getBlindEffect() {
        return BadBoneFabric.BLIND;
    }

    @Override
    public MobEffect getChronoEffect() {
        return BadBoneFabric.CHRONO;
    }

    @Override
    public MobEffect getKneeHurtEffect() {
        return BadBoneFabric.KNEE_HURT;
    }

    @Override
    public MobEffect getBackHurtEffect() {
        return BadBoneFabric.BACK_HURT;
    }

    @Override
    public MobEffect getArthritisEffect() {
        return BadBoneFabric.ARTHRITIS;
    }
}
