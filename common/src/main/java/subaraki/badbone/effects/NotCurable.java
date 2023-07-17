package subaraki.badbone.effects;

import net.minecraft.world.item.ItemStack;

import java.util.List;

public interface NotCurable {
    List<ItemStack> getCurativeItems();
}