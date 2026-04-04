package ltd.opens.mg.mc.network.payloads;

import ltd.opens.mg.mc.MaingraphforMC;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public record SaveResultPayload(boolean success, String message, long newVersion) {
    public static final ResourceLocation ID = new ResourceLocation(MaingraphforMC.MODID, "save_result");

    public static SaveResultPayload decode(FriendlyByteBuf buf) {
        return new SaveResultPayload(buf.readBoolean(), buf.readUtf(), buf.readVarLong());
    }

    public static void encode(FriendlyByteBuf buf, SaveResultPayload payload) {
        buf.writeBoolean(payload.success());
        buf.writeUtf(payload.message());
        buf.writeVarLong(payload.newVersion());
    }
}
