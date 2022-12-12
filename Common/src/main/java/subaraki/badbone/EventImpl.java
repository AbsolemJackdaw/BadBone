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
import subaraki.badbone.platform.IConfigHelper;
import subaraki.badbone.platform.IEffectHelper;
import subaraki.badbone.platform.Services;

public class EventImpl {
    public static final ResourceLocation ADVANCEMENT = new ResourceLocation(BadBone.MODID, "eyesight");

    public static void itemCraftedOrPicked(Player player, ItemStack crafted, MobEffect effect) {
        if (crafted.getItem().equals(Items.CLOCK) && isInSurvivalMode(player)) {
            player.addEffect(new MobEffectInstance(effect, 20 * 90, 0, false, false, true));
        }
    }

    public static void playerHurt(Player player, DamageSource source, float damage, MobEffect effect) {
        //check for damage > 0.4f to prevent non damage falls like slime blocks to generate knee pain
        if (player.fallDistance > 3 && source == DamageSource.FALL && damage > 0.4F
                && player.getRandom().nextInt(Services.CONFIG_HELPER.getFrequencyKnee()) == 0) {
            player.addEffect(new MobEffectInstance(effect, player.getRandom().nextInt(20 * 15) * (int) player.fallDistance, 0, false, false, true));
            playHurtSound(player);
        }
    }

    public static void playerTick(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            IEffectHelper effects = Services.EFFECT_HELPER;
            IConfigHelper config = Services.CONFIG_HELPER;
            if (EventImpl.isInSurvivalMode(player)) {
                if (!serverPlayer.hasEffect(effects.getBackHurtEffect())) {
                    if (serverPlayer.getRandom().nextInt(config.getFrequencyHurt()) == 0 || serverPlayer.getStats().getValue(Stats.CUSTOM.get(Stats.TIME_SINCE_REST)) < 100) {
                        float fullInv = 4 * 9 * 64; //four rows of 9 slots with 64 items in
                        float totalCarrying = 0;
                        for (ItemStack stack : serverPlayer.getInventory().items) {
                            totalCarrying += stack.isEmpty() ? 0f : 64f * ((float) stack.getCount() / (float) stack.getMaxStackSize());
                        }
                        //the closer to 0 weight is, the more the player carries
                        //if the weight is taking up 2/3-ish from the inventory, then you can start having back pain
                        if (totalCarrying > fullInv * 0.65f) {
                            if (serverPlayer.getRandom().nextInt(config.getChanceHurt()) == 0) {
                                serverPlayer.addEffect(new MobEffectInstance(effects.getBackHurtEffect(),
                                        serverPlayer.getRandom().nextInt(20 * 60 * 2) + 20 * 15, 0, false, false, true));
                                EventImpl.playHurtSound(serverPlayer);
                            }
                        }
                    }
                }


                if (serverPlayer.hasEffect(effects.getArthritisEffect())) {
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
                    if (serverPlayer.isUsingItem() && serverPlayer.getRandom().nextInt(config.getFrequencyArthritis()) == 0) {
                        serverPlayer.addEffect(new MobEffectInstance(effects.getArthritisEffect(),
                                serverPlayer.getRandom().nextInt(20 * 60 * 2) + 20 * 15, 0, false, false, true));
                    }
                }

                if (!serverPlayer.hasEffect(effects.getBlindEffect())) {
                    if (serverPlayer.getRandom().nextInt(config.getFrequencyEyes()) == 0) {
                        serverPlayer.addEffect(new MobEffectInstance(effects.getBlindEffect(),
                                serverPlayer.getRandom().nextInt(20 * 60 * 15) + 20 * 60 * 15, 0, false, false, true));
                        serverPlayer.getAdvancements().award(serverPlayer.getServer().getAdvancements().getAdvancement(ADVANCEMENT), "eyesight");
                    }
                }
                if (serverPlayer.hasEffect(effects.getChronoEffect())) {
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
                if (serverPlayer.hasEffect(effects.getArthritisEffect()))
                    serverPlayer.removeEffect(effects.getArthritisEffect());
                if (serverPlayer.hasEffect(effects.getBackHurtEffect()))
                    serverPlayer.removeEffect(effects.getBackHurtEffect());
                if (serverPlayer.hasEffect(effects.getKneeHurtEffect()))
                    serverPlayer.removeEffect(effects.getKneeHurtEffect());
                if (serverPlayer.hasEffect(effects.getChronoEffect()))
                    serverPlayer.removeEffect(effects.getChronoEffect());
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
