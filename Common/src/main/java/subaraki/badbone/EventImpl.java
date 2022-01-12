package subaraki.badbone;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class EventImpl {
    public static final ResourceLocation ADVANCEMENT = new ResourceLocation(BadBone.MODID, "eyesight");

    public static void itemCraftedOrPicked(Player player, ItemStack crafted, MobEffect effect) {
        if (crafted.getItem().equals(Items.CLOCK) && isInSurvivalMode(player)) {
            player.addEffect(new MobEffectInstance(effect, 20 * 90, 0, false, false, true));
        }
    }

    public static void playerHurt(Player player, DamageSource source, float damage, MobEffect effect) {
        //check for damage > 0.4f to prevent non damage falls like slime blocks to generate knee pain
        if (player.fallDistance > 3 && source == DamageSource.FALL && damage > 0.4F && player.getRandom().nextInt(CommonConfig.frequencyKnee) == 0) {
            player.addEffect(new MobEffectInstance(effect, player.getRandom().nextInt(20 * 15) * (int) player.fallDistance, 0, false, false, true));
            playHurtSound(player);
        }
    }

    public static void playerTick(Player player, MobEffect backHurt, MobEffect arthritis, MobEffect kneeHurt, MobEffect chrono, MobEffect blind) {

        if (player instanceof ServerPlayer serverPlayer) {
            if (EventImpl.isInSurvivalMode(player)) {
                if (!serverPlayer.hasEffect(backHurt)) {
                    if (serverPlayer.getRandom().nextInt(CommonConfig.frequencyHurt) == 0 || serverPlayer.getStats().getValue(Stats.CUSTOM.get(Stats.TIME_SINCE_REST)) < 100) {
                        float fullInv = 4 * 9 * 64; //four rows of 9 slots with 64 items in
                        float totalCarrying = 0;
                        for (ItemStack stack : serverPlayer.getInventory().items) {
                            totalCarrying += stack.isEmpty() ? 0f : 64f * ((float) stack.getCount() / (float) stack.getMaxStackSize());
                        }
                        //the closer to 0 weight is, the more the player carries
                        //if the weight is taking up 2/3-ish from the inventory, then you can start having back pain
                        if (totalCarrying > fullInv * 0.65f) {
                            if (serverPlayer.getRandom().nextInt(CommonConfig.chanceHurt) == 0) {
                                serverPlayer.addEffect(new MobEffectInstance(backHurt, serverPlayer.getRandom().nextInt(20 * 60 * 2) + 20 * 15, 0, false, false, true));
                                EventImpl.playHurtSound(serverPlayer);
                            }
                        }
                    }
                }


                if (serverPlayer.hasEffect(arthritis)) {
                    if (serverPlayer.getMainHandItem().getAttributeModifiers(EquipmentSlot.MAINHAND).containsKey(Attributes.ATTACK_DAMAGE)) {
                        serverPlayer.drop(serverPlayer.getMainHandItem(), true);
                        serverPlayer.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
                        EventImpl.playHurtSound(serverPlayer);
                    }
                    if (serverPlayer.getOffhandItem().getAttributeModifiers(EquipmentSlot.OFFHAND).containsKey(Attributes.ATTACK_DAMAGE)) {
                        serverPlayer.drop(serverPlayer.getOffhandItem(), true);
                        serverPlayer.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
                        EventImpl.playHurtSound(serverPlayer);
                    }
                } else {
                    if (serverPlayer.isUsingItem() && serverPlayer.getRandom().nextInt(CommonConfig.frequencyArthritis) == 0) {
                        serverPlayer.addEffect(new MobEffectInstance(arthritis, serverPlayer.getRandom().nextInt(20 * 60 * 2) + 20 * 15, 0, false, false, true));
                    }
                }

                if (!serverPlayer.hasEffect(blind)) {
                    if (serverPlayer.getRandom().nextInt(CommonConfig.frequencyEyes) == 0) {
                        serverPlayer.addEffect(new MobEffectInstance(blind, serverPlayer.getRandom().nextInt(20 * 60 * 15) + 20 * 60 * 15, 0, false, false, true));
                        serverPlayer.getAdvancements().award(serverPlayer.getServer().getAdvancements().getAdvancement(ADVANCEMENT), "eyesight");
                    }
                }
                if (serverPlayer.hasEffect(chrono)) {
                    if (!serverPlayer.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(MobEffects.MOVEMENT_SLOWDOWN.getAttributeModifiers().get(Attributes.MOVEMENT_SPEED)))
                        serverPlayer.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(MobEffects.MOVEMENT_SLOWDOWN.getAttributeModifiers().get(Attributes.MOVEMENT_SPEED));
                } else {
                    if (serverPlayer.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(MobEffects.MOVEMENT_SLOWDOWN.getAttributeModifiers().get(Attributes.MOVEMENT_SPEED)))
                        serverPlayer.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(MobEffects.MOVEMENT_SLOWDOWN.getAttributeModifiers().get(Attributes.MOVEMENT_SPEED));

                }
            }
            //heal even if in creative
            BlockPos bubble = serverPlayer.blockPosition();
            BlockPos magma = serverPlayer.getOnPos();
            Level level = serverPlayer.level;
            if (level.getBlockState(bubble).is(Blocks.BUBBLE_COLUMN) && level.getBlockState(magma).is(Blocks.MAGMA_BLOCK)) {
                if (serverPlayer.hasEffect(arthritis))
                    serverPlayer.removeEffect(arthritis);
                if (serverPlayer.hasEffect(backHurt))
                    serverPlayer.removeEffect(backHurt);
                if (serverPlayer.hasEffect(kneeHurt))
                    serverPlayer.removeEffect(kneeHurt);
                if (serverPlayer.hasEffect(chrono))
                    serverPlayer.removeEffect(chrono);
            }
        }
    }

    public static boolean isInSurvivalMode(Player player) {
        return !player.isSpectator() && !player.isCreative();
    }

    public static void playHurtSound(Player player) {
        player.playNotifySound(SoundEvents.BONE_BLOCK_BREAK, SoundSource.PLAYERS, 1f, player.getRandom().nextFloat() + 1f);
        player.playNotifySound(SoundEvents.PLAYER_HURT, SoundSource.PLAYERS, 1f, player.getRandom().nextFloat() + 1f);
    }
}
