package subaraki.badbone.events;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import subaraki.badbone.mod.BadBone;
import subaraki.badbone.mod.ConfigData;
import subaraki.badbone.registry.BadBoneEffects;

@Mod.EventBusSubscriber(modid = BadBone.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerUpdateEvent {

    private final static ResourceLocation ADVANCEMENT = new ResourceLocation(BadBone.MODID, "eyesight");

    @SubscribeEvent
    public static void playerUpdate(TickEvent.PlayerTickEvent event) {
        if (event.player instanceof ServerPlayer player) {
            if (!player.hasEffect(BadBoneEffects.BACK_HURT.get())) {
                if (player.getRandom().nextInt(ConfigData.frequencyHurt) == 0 || player.getStats().getValue(Stats.CUSTOM.get(Stats.TIME_SINCE_REST)) < 100) {
                    int weight = 0;
                    int quota = 0;
                    for (ItemStack stack : player.getInventory().items) {
                        if (!stack.isEmpty()) {
                            quota += stack.getMaxStackSize();
                            weight += stack.getMaxStackSize() - stack.getCount();
                        }
                    }
                    //the closer to 0 weight is, the more the player carries
                    //if the weight is taking up 2/3-ish from the inventory, then you can start having back pain
                    if (weight < quota * 0.35f) {
                        if (player.getRandom().nextInt(ConfigData.chanceHurt) == 0) {
                            player.addEffect(new MobEffectInstance(BadBoneEffects.BACK_HURT.get(), player.getRandom().nextInt(20 * 60 * 2) + 20 * 15, 0, false, false, true));
                            playHurtSound(player);
                        }
                    }
                }
            }

            if (player.hasEffect(BadBoneEffects.ARTHRITIS.get())) {
                if (player.getMainHandItem().getAttributeModifiers(EquipmentSlot.MAINHAND).containsKey(Attributes.ATTACK_DAMAGE)) {
                    player.drop(player.getMainHandItem(), true);
                    player.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
                    playHurtSound(player);
                }
                if (player.getOffhandItem().getAttributeModifiers(EquipmentSlot.OFFHAND).containsKey(Attributes.ATTACK_DAMAGE)) {
                    player.drop(player.getOffhandItem(), true);
                    player.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
                    playHurtSound(player);
                }
            } else {
                if (player.isUsingItem() && player.getRandom().nextInt(ConfigData.frequencyArthritis) == 0) {
                    player.addEffect(new MobEffectInstance(BadBoneEffects.ARTHRITIS.get(), player.getRandom().nextInt(20 * 60 * 2) + 20 * 15, 0, false, false, true));
                }
            }

            if (!player.hasEffect(BadBoneEffects.BLIND.get())) {
                if (player.getRandom().nextInt(ConfigData.frequencyEyes) == 0) {
                    player.addEffect(new MobEffectInstance(BadBoneEffects.BLIND.get(), player.getRandom().nextInt(20 * 60 * 15) + 20 * 60 * 15, 0, false, false, true));
                    player.getAdvancements().award(player.getServer().getAdvancements().getAdvancement(ADVANCEMENT), "eyesight");
                }
            }
            if (player.hasEffect(BadBoneEffects.CHRONO.get())) {
                if (!player.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(MobEffects.MOVEMENT_SLOWDOWN.getAttributeModifiers().get(Attributes.MOVEMENT_SPEED)))
                    player.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(MobEffects.MOVEMENT_SLOWDOWN.getAttributeModifiers().get(Attributes.MOVEMENT_SPEED));
            } else {
                if (player.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(MobEffects.MOVEMENT_SLOWDOWN.getAttributeModifiers().get(Attributes.MOVEMENT_SPEED)))
                    player.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(MobEffects.MOVEMENT_SLOWDOWN.getAttributeModifiers().get(Attributes.MOVEMENT_SPEED));

            }
        }
    }

    @SubscribeEvent
    public static void playerFall(LivingHurtEvent event) {
        if (event.getEntityLiving() instanceof Player player) {
            if (player.fallDistance > 3 && event.getSource().equals(DamageSource.FALL) && player.getRandom().nextInt(ConfigData.frequencyKnee) == 0) {
                player.addEffect(new MobEffectInstance(BadBoneEffects.KNEE_HURT.get(), player.getRandom().nextInt(20 * 15) * (int) player.fallDistance, 0, false, false, true));
                playHurtSound(player);
            }
        }
    }

    @SubscribeEvent
    public static void playerPickup(PlayerEvent.ItemPickupEvent event) {
        if (event.getStack().getItem().equals(Items.CLOCK)) {
            event.getPlayer().addEffect(new MobEffectInstance(BadBoneEffects.CHRONO.get(), 20 * 90, 0, false, false, true));

        }
    }

    @SubscribeEvent
    public static void playerCraft(PlayerEvent.ItemCraftedEvent event) {
        if (event.getCrafting().getItem().equals(Items.CLOCK)) {
            event.getPlayer().addEffect(new MobEffectInstance(BadBoneEffects.CHRONO.get(), 20 * 90, 0, false, false, true));
        }
    }

    private static void playHurtSound(Player player) {
        player.playNotifySound(SoundEvents.BONE_BLOCK_BREAK, SoundSource.PLAYERS, 1f, player.getRandom().nextFloat() + 1f);
        player.playNotifySound(SoundEvents.PLAYER_HURT, SoundSource.PLAYERS, 1f, player.getRandom().nextFloat() + 1f);
    }
}
