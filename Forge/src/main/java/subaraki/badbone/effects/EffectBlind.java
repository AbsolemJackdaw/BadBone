package subaraki.badbone.effects;

import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class EffectBlind extends CommonEffectBlind {

    @Override
    public List<ItemStack> getCurativeItems() {
        return new ArrayList<>();
    }
}