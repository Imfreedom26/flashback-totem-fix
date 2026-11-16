package com.flashbacktotem.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class TotemReplayMixin {

    @Inject(method = "onEntityStatus", at = @At("TAIL"))
    private void onTotemPopInReplay(EntityStatusS2CPacket packet, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null) return;

        if (packet.getStatus() == EntityStatuses.USE_TOTEM_OF_UNDYING) {
            Entity targetEntity = packet.getEntity(client.world);
            Entity cameraEntity = client.getCameraEntity();

            if (targetEntity != null && targetEntity == cameraEntity && targetEntity != client.player) {
                client.gameRenderer.showFloatingItem(new ItemStack(Items.TOTEM_OF_UNDYING));
            }
        }
    }
}
