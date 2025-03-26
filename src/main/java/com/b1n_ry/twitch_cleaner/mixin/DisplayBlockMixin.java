package com.b1n_ry.twitch_cleaner.mixin;

import com.b1n_ry.twitch_cleaner.TwitchCleaner;
import me.srrapero720.waterframes.common.block.DisplayBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashSet;

@Mixin(DisplayBlock.class)
public class DisplayBlockMixin {
    @Inject(method = "use", at = @At("HEAD"))
    private void interact(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit, CallbackInfoReturnable<InteractionResult> cir) {
        TwitchCleaner.INTERACTED_SCREENS.computeIfAbsent(player.getUUID(), uuid -> new HashSet<>())
                .add(GlobalPos.of(level.dimension(), pos));
    }
}
