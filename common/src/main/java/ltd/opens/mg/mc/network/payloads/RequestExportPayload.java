package ltd.opens.mg.mc.network.payloads;

import ltd.opens.mg.mc.MaingraphforMC;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public record RequestExportPayload(String name) {
    public static final ResourceLocation ID = new ResourceLocation(MaingraphforMC.MODID, "request_export");

    public static RequestExportPayload decode(FriendlyByteBuf buf) {
        return new RequestExportPayload(buf.readUtf());
    }

    public static void encode(FriendlyByteBuf buf, RequestExportPayload payload) {
        buf.writeUtf(payload.name());
    }
}
