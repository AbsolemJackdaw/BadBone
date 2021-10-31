package subaraki.badbone.items;

import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Wearable;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class GlassesItem extends Item implements Wearable {
    private static final Properties PROPERTIES = new Properties().tab(CreativeModeTab.TAB_TOOLS);

    public GlassesItem() {
        super(PROPERTIES);
    }

    @Nullable
    @Override
    public EquipmentSlot getEquipmentSlot(ItemStack stack) {
        return EquipmentSlot.HEAD;
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack glasses = player.getItemInHand(hand);
        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
        if (helmet.isEmpty()) {
            player.setItemSlot(EquipmentSlot.HEAD, glasses.copy());
            if (!level.isClientSide()) {
                player.awardStat(Stats.ITEM_USED.get(this));
            }

            glasses.setCount(0);
            return InteractionResultHolder.sidedSuccess(glasses, level.isClientSide());
        } else {
            return InteractionResultHolder.fail(glasses);
        }
    }
}
