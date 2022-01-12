package subaraki.badbone.effects;

import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class EffectWeakKnees extends CommonEffectWeakKnees {
    @Override
    public List<ItemStack> getCurativeItems() {
        return new ArrayList<>();
    }
}
