package com.flashbacktotem.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundEntityEventPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public class TotemReplayMixin {

    @Inject(method = "handleEntityEvent", at = @At("TAIL"))
    private void onTotemPopInReplay(ClientboundEntityEventPacket packet, CallbackInfo ci) {
        Minecraft client = Minecraft.getInstance();
        if (client.level == null) return;

        // 35 is the Totem of Undying pop event ID
        if (packet.getEventId() == 35) {
            Entity targetEntity = packet.getEntity(client.level);
            Entity cameraEntity = client.getCameraEntity();

            if (targetEntity != null && targetEntity == cameraEntity && targetEntity != client.player) {
                client.gameRenderer.displayItemActivation(new ItemStack(Items.TOTEM_OF_UNDYING));
            }
        }
    }
}
