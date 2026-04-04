package ltd.opens.mg.mc.network.payloads;

import ltd.opens.mg.mc.MaingraphforMC;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public record RuntimeErrorReportPayload(String blueprintName, String nodeId, String message) {
    public static final ResourceLocation ID = new ResourceLocation(MaingraphforMC.MODID, "runtime_error_report");

    public static RuntimeErrorReportPayload decode(FriendlyByteBuf buf) {
        return new RuntimeErrorReportPayload(buf.readUtf(), buf.readUtf(), buf.readUtf());
    }

    public static void encode(FriendlyByteBuf buf, RuntimeErrorReportPayload payload) {
        buf.writeUtf(payload.blueprintName());
        buf.writeUtf(payload.nodeId());
        buf.writeUtf(payload.message());
    }
}
