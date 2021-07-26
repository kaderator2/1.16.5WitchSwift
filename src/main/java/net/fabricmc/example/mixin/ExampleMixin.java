package net.fabricmc.example.mixin;
import net.minecraft.item.ItemStack;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.MessageType;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.text.LiteralText;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.WitchEntity;
import net.minecraft.client.MinecraftClientGame;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.UseAction;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import java.awt.event.*;
import java.awt.Robot;
import java.awt.AWTException;
import net.minecraft.network.ClientConnection;


@Mixin(ClientPlayerEntity.class)
public class ExampleMixin {
	@Inject(at = @At("HEAD"), method = "tick()V")
	private void init(CallbackInfo info) {
		boolean cooldown = false;
		int counter = 0;
		MinecraftClient client = MinecraftClient.getInstance();
		ClientPlayerEntity player = client.player;
		Iterable<Entity> listOfEntities = ((ClientWorld) client.player.world).getEntities();
		char[] PacketListener;
		System.out.println(PacketListener);
		for (Entity renderedEntity : listOfEntities){
			if (renderedEntity instanceof WitchEntity && cooldown == false){
				client.inGameHud.addChatMessage(MessageType.SYSTEM, new LiteralText("§k§0-§r   §a§lTHERE IS A WITCH§r   §k§0-§r"), client.player.getUuid());
				if (((WitchEntity) renderedEntity).isDrinking()){
					client.inGameHud.addChatMessage(MessageType.SYSTEM, new LiteralText("§k§0-§r   §a§l*we is drinking*§r   §k§0-§r"), client.player.getUuid());
					//UseAction temp = UseAction.BLOCK;
					cooldown = true;
					//client.player.networkHandler.sendPacket(new PlayerInteractItemC2SPacket(Hand.MAIN_HAND));
					//PlayerActionC2SPacket(PlayerActionC2SPacket.Action.RELEASE_USE_ITEM, player.getBlockPos(), player.getEyeY());
				}
			}
			if (cooldown == true){
				counter += 1;
			}
			if (counter > 100){
				cooldown = false;
				counter = 0;
			}
		}
	}
}
