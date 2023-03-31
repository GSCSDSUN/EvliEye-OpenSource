/*

 *



 */
package gg.evlieye.mixin;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.resource.SynchronousResourceReloader;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import gg.evlieye.EvlieyeClient;
import gg.evlieye.event.EventManager;
import gg.evlieye.events.CameraTransformViewBobbingListener.CameraTransformViewBobbingEvent;
import gg.evlieye.events.HitResultRayTraceListener.HitResultRayTraceEvent;
import gg.evlieye.events.RenderListener.RenderEvent;
import gg.evlieye.hacks.FullbrightHack;
import gg.evlieye.mixinterface.IGameRenderer;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin
	implements AutoCloseable, SynchronousResourceReloader, IGameRenderer
{
	private boolean cancelNextBobView;
	
	@Inject(at = @At(value = "INVOKE",
		target = "Lnet/minecraft/client/render/GameRenderer;bobView(Lnet/minecraft/client/util/math/MatrixStack;F)V",
		ordinal = 0),
		method = "renderWorld(FJLnet/minecraft/client/util/math/MatrixStack;)V")
	private void onRenderWorldViewBobbing(float tickDelta, long limitTime,
		MatrixStack matrices, CallbackInfo ci)
	{
		CameraTransformViewBobbingEvent event =
			new CameraTransformViewBobbingEvent();
		EventManager.fire(event);
		
		if(event.isCancelled())
			cancelNextBobView = true;
	}
	
	@Inject(at = @At("HEAD"),
		method = "bobView(Lnet/minecraft/client/util/math/MatrixStack;F)V",
		cancellable = true)
	private void onBobView(MatrixStack matrices, float tickDelta,
		CallbackInfo ci)
	{
		if(!cancelNextBobView)
			return;
		
		ci.cancel();
		cancelNextBobView = false;
	}
	
	@Inject(at = @At("HEAD"),
		method = "renderHand(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/Camera;F)V")
	private void renderHand(MatrixStack matrices, Camera camera,
		float tickDelta, CallbackInfo ci)
	{
		cancelNextBobView = false;
	}
	
	@Inject(
		at = @At(value = "FIELD",
			target = "Lnet/minecraft/client/render/GameRenderer;renderHand:Z",
			opcode = Opcodes.GETFIELD,
			ordinal = 0),
		method = "renderWorld(FJLnet/minecraft/client/util/math/MatrixStack;)V")
	private void onRenderWorld(float partialTicks, long finishTimeNano,
		MatrixStack matrixStack, CallbackInfo ci)
	{
		RenderEvent event = new RenderEvent(matrixStack, partialTicks);
		EventManager.fire(event);
	}
	
	@Inject(at = @At(value = "RETURN", ordinal = 1),
		method = "getFov(Lnet/minecraft/client/render/Camera;FZ)D",
		cancellable = true)
	private void onGetFov(Camera camera, float tickDelta, boolean changingFov,
		CallbackInfoReturnable<Double> cir)
	{
		cir.setReturnValue(EvlieyeClient.INSTANCE.getOtfs().zoomOtf
			.changeFovBasedOnZoom(cir.getReturnValueD()));
	}
	
	@Inject(at = @At(value = "INVOKE",
		target = "Lnet/minecraft/entity/Entity;getCameraPosVec(F)Lnet/minecraft/util/math/Vec3d;",
		opcode = Opcodes.INVOKEVIRTUAL,
		ordinal = 0), method = "updateTargetedEntity(F)V")
	private void onHitResultRayTrace(float float_1, CallbackInfo ci)
	{
		HitResultRayTraceEvent event = new HitResultRayTraceEvent(float_1);
		EventManager.fire(event);
	}
	
	@Redirect(
		at = @At(value = "INVOKE",
			target = "Lnet/minecraft/util/math/MathHelper;lerp(FFF)F",
			ordinal = 0),
		method = "renderWorld(FJLnet/minecraft/client/util/math/MatrixStack;)V")
	private float evlieyeNauseaLerp(float delta, float first, float second)
	{
		if(!EvlieyeClient.INSTANCE.getHax().antiWobbleHack.isEnabled())
			return MathHelper.lerp(delta, first, second);
		
		return 0;
	}
	
	@Inject(at = @At("HEAD"),
		method = "getNightVisionStrength(Lnet/minecraft/entity/LivingEntity;F)F",
		cancellable = true)
	private static void onGetNightVisionStrength(LivingEntity livingEntity,
		float f, CallbackInfoReturnable<Float> cir)
	{
		FullbrightHack fullbright =
			EvlieyeClient.INSTANCE.getHax().fullbrightHack;
		
		if(fullbright.isNightVisionActive())
			cir.setReturnValue(fullbright.getNightVisionStrength());
	}
	
	@Inject(at = @At("HEAD"),
		method = "tiltViewWhenHurt(Lnet/minecraft/client/util/math/MatrixStack;F)V",
		cancellable = true)
	private void onTiltViewWhenHurt(MatrixStack matrixStack, float f,
		CallbackInfo ci)
	{
		if(EvlieyeClient.INSTANCE.getHax().noHurtcamHack.isEnabled())
			ci.cancel();
	}
	
	@Shadow
	private void bobView(MatrixStack matrixStack, float partalTicks)
	{
		
	}
	
	@Override
	public void loadEvlieyeShader(Identifier id)
	{
		loadPostProcessor(id);
	}
	
	@Shadow
	private void loadPostProcessor(Identifier id)
	{
		
	}
}
